package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.mapper.ManagerMapper;
import com.bitprogress.util.BcryptUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import com.bitprogress.constant.ManagerConstant;
import com.bitprogress.entity.Manager;
import com.bitprogress.model.manager.envm.RoleEnum;
import com.bitprogress.model.manager.pojo.cms.*;
import com.bitprogress.util.Assert;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;

/**
 * @author wuwuwupx
 */
@Slf4j
@Service
public class ManagerService extends ServiceImpl<ManagerMapper, Manager> {

    @Value("${application.config.salt}")
    private String salt;

    private final String rootManagerName = ManagerConstant.ROOT_MANAGER_NAME;

    @Autowired
    private LoginService loginService;

    /**
     * 检查和添加超管账号
     */
    @PostConstruct
    public void addSupperManager() {
        boolean hasSupperManger = checkSupperManager();
        if (hasSupperManger) {
            return;
        }
        Manager manager = new Manager();
        manager.setAccount(rootManagerName);
        manager.setPassword(DigestUtils.md5DigestAsHex((rootManagerName + salt).getBytes()));
        manager.setUsername("ROOT");
        manager.setRole(RoleEnum.ROOT);
        manager.setDisabled(false);
        save(manager);
    }

    /**
     * 检查是否有超管账号
     */
    public boolean checkSupperManager() {
        LambdaQueryWrapper<Manager> lambda = new QueryWrapper<Manager>().lambda();
        lambda.eq(Manager::getAccount, rootManagerName);
        return count(lambda) > 0;
    }

    /**
     * 获取管理员的信息
     *
     * @param managerId
     * @param userId
     */
    public ManagerCmsVO findById(Integer managerId, Long userId) {
        Manager manager = getById(userId);
        Assert.isTrue(Objects.equals(RoleEnum.ROOT, manager.getRole()) || Objects.equals(managerId, userId),
                BaseExceptionMessage.NOT_ROOT_CHECK_OTHER_INFO_EXCEPTION);
        Manager checkManager = getById(managerId);
        Assert.notNull(checkManager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);
        return toManagerPageVO(manager);
    }

    /**
     * 获得管理员列表
     *
     * @param queryDTO
     * @param page
     * @return Page<ManagerCmsVO>
     */
    public IPage<ManagerCmsVO> findPage(ManagerCmsQueryDTO queryDTO, Page page) {
        String username = queryDTO.getUsername();
        LambdaQueryWrapper<Manager> lambda = new QueryWrapper<Manager>().lambda();
        if (StringUtils.nonEmpty(username)) {
            lambda.like(Manager::getUsername, queryDTO.getUsername());
        }
        return PageUtils.conversionBean(page(page, lambda), this::toManagerPageVO);
    }

    /**
     * Manager -> ManagerCmsVO
     *
     * @param manager
     * @return ManagerCmsVO
     */
    private ManagerCmsVO toManagerPageVO(Manager manager) {
        ManagerCmsVO pageVO = new ManagerCmsVO();
        BeanUtils.copyNonNullProperties(manager, pageVO);
        return pageVO;
    }

    /**
     * 添加管理员
     *
     * @param addDTO
     * @return ManagerCmsVO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ManagerCmsVO add(ManagerCmsAddDTO addDTO) {
        String account = addDTO.getAccount();
        if (!Objects.equals(rootManagerName, account) && addDTO.getRole().equals(RoleEnum.ROOT)) {
            throw new CommonException(BaseExceptionMessage.ALLOW_ADD_WPX_EXCEPT_ROOT_EXCEPTION);
        }

        String password;
        try {
            password = DigestUtils.md5DigestAsHex((BcryptUtils.decrypt(addDTO.getPassword()) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new CommonException(ExceptionMessage.RSAUtil_DECRYPT_ERROR);
        }
        LambdaQueryWrapper<Manager> lambda = new QueryWrapper<Manager>().lambda();
        lambda.eq(Manager::getAccount, account);
        Assert.isTrue(count(lambda) == 0, ExceptionMessage.ACCOUNT_ALREADY_EXIST);
        Manager manager = BeanUtils.copyNonNullProperties(addDTO, Manager.class);
        manager.setPassword(password);
        Assert.isTrue(save(manager), BaseExceptionMessage.MANAGER_SAVE_EXCEPTION);
        return toManagerPageVO(manager);
    }

    /**
     * 更新管理员信息，仅包括用户名
     *
     * @param updateDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ManagerCmsVO update(ManagerCmsUpdateDTO updateDTO, Long userId) {
        Long managerId = updateDTO.getManagerId();
        RoleEnum role = updateDTO.getRole();
        Manager manager = getById(managerId);
        Assert.notNull(manager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);
        // 非超管只能编辑自己的信息
        if (!Objects.equals(manager.getRole(), RoleEnum.ROOT) && !Objects.equals(managerId, userId)) {
            throw new CommonException(BaseExceptionMessage.NOT_ROOT_EDIT_OTHER_INFO_EXCEPTION);
        }
        // 检查角色权限
        if (!Objects.equals(rootManagerName, manager.getAccount()) && Objects.equals(RoleEnum.ROOT, role)) {
            throw new CommonException(BaseExceptionMessage.ALLOW_ADD_WPX_EXCEPT_ROOT_EXCEPTION);
        }
        manager.setRole(role);
        manager.setUsername(updateDTO.getUsername());
        return toManagerPageVO(manager);
    }

    /**
     * 开启或禁用管理员，超管才可以操作
     *
     * @param managerStateDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ManagerCmsVO handleDisabled(ManagerStateDTO managerStateDTO, Long userId) {
        Manager manager = getById(userId);
        Assert.isTrue(Objects.equals(RoleEnum.ROOT, manager.getRole()), BaseExceptionMessage.NOT_ROOT_DISABLED_EXCEPTION);

        Long managerId = managerStateDTO.getManagerId();
        Manager handleManager = getById(managerId);
        Assert.notNull(handleManager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);
        Assert.isTrue(!Objects.equals(RoleEnum.ROOT, handleManager.getRole()), BaseExceptionMessage.ROOT_DISABLED_EXCEPTION);
        handleManager.setDisabled(!handleManager.getDisabled());
        if (handleManager.getDisabled()) {

        }
        Assert.isTrue(updateById(handleManager), BaseExceptionMessage.MANAGER_UPDATE_EXCEPTION);
        return toManagerPageVO(handleManager);
    }

    /**
     * 删除管理员，超管才可以进行账号删除
     *
     * @param managerIds
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Set<Long> managerIds, Long userId) {
        if (CollectionUtils.isEmpty(managerIds)) {
            return;
        }
        Manager manager = getById(userId);
        Assert.isTrue(Objects.equals(RoleEnum.ROOT, manager.getRole()), BaseExceptionMessage.NOT_ROOT_DELETE_ACCOUNT_EXCEPTION);
        managerIds.forEach(this::deleteManager);
    }

    /**
     * 删除管理员
     *
     * @param managerId
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteManager(Long managerId) {
        Manager manager = getById(managerId);
        if (Objects.nonNull(manager)) {
            Assert.isTrue(!Objects.equals(RoleEnum.ROOT, manager.getRole()), BaseExceptionMessage.ROOT_DELETE_EXCEPTION);
            loginService.removeToken(String.valueOf(managerId));
            manager.setDeleted(true);
            Assert.isTrue(updateById(manager), BaseExceptionMessage.MANAGER_UPDATE_EXCEPTION);
        }
    }

    /**
     * 修改密码，超管可以对所有账号的密码进行更改，非超管只能更改自己的密码
     *
     * @param managerResetPasswordDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ManagerCmsVO resetPassword(ManagerResetPasswordDTO managerResetPasswordDTO, Long userId) {
        Long managerId = managerResetPasswordDTO.getManagerId();
        Manager manager = getById(managerId);
        Assert.notNull(manager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);
        if (!Objects.equals(manager.getRole(), RoleEnum.ROOT) && !Objects.equals(managerId, userId)) {
            throw new CommonException(BaseExceptionMessage.NOT_ROOT_EDIT_OTHER_INFO_EXCEPTION);
        }
        String unencryptedPassword = managerResetPasswordDTO.getPassword();
        String md5Password;
        try {
            md5Password = DigestUtils.md5DigestAsHex((BcryptUtils.decrypt(unencryptedPassword) + salt).getBytes());
        } catch (Exception e) {
            log.error("RSAUtil.decrypt exception", e);
            throw new CommonException(ExceptionMessage.RSAUtil_DECRYPT_ERROR);
        }
        manager.setPassword(md5Password);
        Assert.isTrue(updateById(manager), BaseExceptionMessage.MANAGER_UPDATE_EXCEPTION);
        return toManagerPageVO(manager);
    }

    /**
     * 更改角色，只要超管可以进行
     *
     * @param managerRoleDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ManagerCmsVO handleRole(ManagerRoleDTO managerRoleDTO, Long userId) {
        Manager manager = getById(managerRoleDTO.getManagerId());
        Assert.notNull(manager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);

        Manager currentManager = getById(userId);
        Assert.notNull(currentManager, BaseExceptionMessage.MANAGER_NOT_EXIST_EXCEPTION);

        Assert.isTrue(currentManager.getRole().ordinal() < manager.getRole().ordinal(), BaseExceptionMessage.ALLOW_SUPERIOR_ROLE_EXCEPTION);
        manager.setRole(managerRoleDTO.getRole());
        Assert.isTrue(updateById(manager), BaseExceptionMessage.MANAGER_UPDATE_EXCEPTION);

        return toManagerPageVO(manager);
    }

    /**
     * 通过账号和密码获取管理员信息
     *
     * @param account
     * @param md5Password
     */
    public Manager getByAccountWithPassword(String account, String md5Password) {
        LambdaQueryWrapper<Manager> lambda = new QueryWrapper<Manager>().lambda();
        lambda.eq(Manager::getAccount, account).eq(Manager::getPassword, md5Password);
        return getOne(lambda);
    }
}
