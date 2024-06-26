package com.bitprogress.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private static final String FOLDER_SEPARATOR = "/";
    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * 判断指定路径是否存在
     *
     * @param filePath 指定的文件路径
     * @return 存在返回TRUE，不存在返回FALSE
     */
    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断指定路径是否存在，如果不存在，根据参数决定是否新建
     *
     * @param filePath 指定的文件路径
     * @param creatNew true：新建、false：不新建
     * @return 存在返回TRUE，不存在返回FALSE
     */
    public static boolean isExist(String filePath, boolean creatNew) {
        File file = new File(filePath);
        if (!file.exists() && creatNew) {
            return file.mkdirs();    //新建文件路径
        }
        return false;
    }

    /**
     * 获取指定文件的大小
     * 文件夹则遍历文件夹下的文件并计算文件大小
     *
     * @param file 指定文件
     * @return 文件大小
     */
    public static long getFileSize(File file) {
        if (Objects.isNull(file)) {
            return 0L;
        }
        if (file.exists()) {
            if (file.isFile()) {
                return file.length();
            } else {
                File[] files = file.listFiles();
                if (Objects.isNull(files)) {
                    return 0L;
                }
                long size = 0L;
                for (File f : files) {
                    size += getFileSize(f);
                }
                return size;
            }
        } else {
            return 0;
        }
    }

    /**
     * 删除所有文件，包括文件夹
     *
     * @param dirPath 文件路径
     */
    public void deleteAll(String dirPath) {
        File path = new File(dirPath);
        try {
            if (!path.exists())
                return;
            if (path.isFile()) {
                path.delete();
                return;
            }
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteAll(file.getAbsolutePath());
                }
            }
            path.delete();
        } catch (Exception e) {
            log.error("dir delete error ", e);
            throw new RuntimeException("文件夹删除失败", e);
        }
    }

    /**
     * 复制文件或者文件夹
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖文件
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (!inputFile.exists()) {
            throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
        }
        copyPri(inputFile, outputFile, isOverWrite);
    }

    /**
     * 复制文件或者文件夹
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖文件
     * @throws IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (inputFile.isFile()) {        //文件
            copySimpleFile(inputFile, outputFile, isOverWrite);
        } else {
            if (!outputFile.exists()) {        //文件夹
                outputFile.mkdirs();
            }
            // 循环子文件夹
            for (File child : inputFile.listFiles()) {
                copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖
     */
    private static void copySimpleFile(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (outputFile.exists()) {
            if (isOverWrite) {        //可以覆盖
                if (!outputFile.delete()) {
                    throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
                }
            } else {
                // 不允许覆盖
                return;
            }
        }
        InputStream in = Files.newInputStream(inputFile.toPath());
        OutputStream out = Files.newOutputStream(outputFile.toPath());
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        out.close();
    }

    /**
     * 获取文件的MD5
     *
     * @param file 文件
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte[] buffer = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获取文件的后缀
     *
     * @param file 文件
     */
    public static String getFileSuffix(String file) {
        if (file == null) {
            return null;
        }
        int extIndex = file.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = file.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return file.substring(extIndex + 1);
    }

    /**
     * 文件重命名
     *
     * @param oldPath 老文件
     * @param newPath 新文件
     * @author : chenssy
     * @date : 2016年5月23日 下午12:56:05
     */
    public boolean renameDir(String oldPath, String newPath) {
        File oldFile = new File(oldPath);// 文件或目录
        File newFile = new File(newPath);// 文件或目录

        return oldFile.renameTo(newFile);// 重命名
    }

    /**
     * Copies bytes from an {@link InputStream} <code>source</code> to a file
     * <code>destination</code>. The directories up to <code>destination</code>
     * will be created if they don't already exist. <code>destination</code>
     * will be overwritten if it already exists.
     * The {@code source} stream is closed.
     * See {@link #copyToFile(InputStream, File)} for a method that does not close the input stream.
     *
     * @param source      the <code>InputStream</code> to copy bytes from, must not be {@code null}, will be closed
     * @param destination the non-directory <code>File</code> to write bytes to
     *                    (possibly overwriting), must not be {@code null}
     * @throws IOException if <code>destination</code> is a directory
     * @throws IOException if <code>destination</code> cannot be written
     * @throws IOException if <code>destination</code> needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     *                     created on 2.0
     */
    public static void copyInputStreamToFile(final InputStream source, final File destination) throws IOException {
        try (InputStream in = source) {
            copyToFile(in, destination);
        }
    }

    /**
     * Copies bytes from an {@link InputStream} <code>source</code> to a file
     * <code>destination</code>. The directories up to <code>destination</code>
     * will be created if they don't already exist. <code>destination</code>
     * will be overwritten if it already exists.
     * The {@code source} stream is left open, e.g. for use with {@link java.util.zip.ZipInputStream ZipInputStream}.
     * See {@link #copyInputStreamToFile(InputStream, File)} for a method that closes the input stream.
     *
     * @param source      the <code>InputStream</code> to copy bytes from, must not be {@code null}
     * @param destination the non-directory <code>File</code> to write bytes to
     *                    (possibly overwriting), must not be {@code null}
     * @throws IOException if <code>destination</code> is a directory
     * @throws IOException if <code>destination</code> cannot be written
     * @throws IOException if <code>destination</code> needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     *                     created on 2.5
     */
    public static void copyToFile(final InputStream source, final File destination) throws IOException {
        try (InputStream in = source;
             OutputStream out = openOutputStream(destination)) {
            IOUtils.copy(in, out);
        }
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist.
     * The file will be created if it does not exist.
     * An exception is thrown if the file object exists but is a directory.
     * An exception is thrown if the file exists but cannot be written to.
     * An exception is thrown if the parent directory cannot be created.
     *
     * @param file the file to open for output, must not be {@code null}
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     *                     created on 1.3
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist.
     * The file will be created if it does not exist.
     * An exception is thrown if the file object exists but is a directory.
     * An exception is thrown if the file exists but cannot be written to.
     * An exception is thrown if the parent directory cannot be created.
     *
     * @param file   the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the
     *               end of the file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     *                     created on 2.1
     */
    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

}