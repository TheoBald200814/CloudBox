package cloudbox.file.ServiceImpl;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FTP文件传输类
 * @author TheoBald
 * @version 0.0.1
 */
@Service
public class FtpClientUtil {

    private static final String FTP_HOST = "47.115.208.235";
    // FTP服务器地址
    private static final int FTP_PORT = 21;
    // FTP服务器端口号
    private static final String FTP_USER = "ftptest";
    // FTP登录用户名
    private static final String FTP_PASS = "qpddf_234";
    // FTP登录密码
    private static final String FTP_BASEPATH = "/home/data/ftptest/";
    // FTP服务器基础路径


    /**
     * 文件上传方法
     * @param file 文件数据
     * @param remotePath 上传路径
     * @param fileToken 文件令牌
     * @return 上传成功，返回true；上传失败，返回false
     */
    boolean uploadFile(MultipartFile file, String remotePath, String fileToken) {

        boolean result = false;

        FTPClient ftpClient = new FTPClient();

        try {
            //建立FTP服务器连接
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            if (!ftpClient.changeWorkingDirectory(FTP_BASEPATH + remotePath)) {
                //如果路径不存在，则创建该路径并跳转到该路径
                ftpClient.makeDirectory(FTP_BASEPATH + remotePath);
                ftpClient.changeWorkingDirectory(FTP_BASEPATH + remotePath);
            }

            InputStream inputStream = file.getInputStream();
            //文件转输入流
            result = ftpClient.storeFile(fileToken, inputStream);
            //执行FTP文件传输
            inputStream.close();
            //输入流关闭
            ftpClient.logout();
            //FTP服务器连接关闭
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 文件下载方法
     * @param fileToken 文件令牌
     * @param remotePath 下载路径
     * @param fileName 文件原名
     * @param response 前端响应
     * @throws IOException
     */
    void downloadFile(String fileToken, String remotePath, String fileName, HttpServletResponse response) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            //建立FTP服务器连接
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 设置工作目录
            ftpClient.changeWorkingDirectory(FTP_BASEPATH + remotePath);

            // 下载文件并输出到前端
            String remoteFile = fileToken;
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            ftpClient.retrieveFile(remoteFile, outputStream);
            outputStream.flush();
        } finally {
            // 断开连接
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    /**
     * 文件更新方法（更新文件位置和文件名）
     * @param from 文件原路径（基础路径为/home/data/ftptest/）
     * @param to 文件目的路径（基础路径同上）
     * @return 更新成功返回true；更新失败返回false
     * @throws IOException
     */
    boolean updateFileToken(String from, String to) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            //建立FTP服务器连接
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.changeWorkingDirectory(FTP_BASEPATH );
            // 设置工作目录
            return ftpClient.rename(from,to);
            //修改文件名（令牌）
        } finally {
            // 断开连接
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public boolean deleteFile(String remotePath) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            //建立FTP服务器连接
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.changeWorkingDirectory(FTP_BASEPATH );
            // 设置工作目录
            return ftpClient.deleteFile(remotePath);
        } finally {
            // 断开连接
            ftpClient.logout();
            ftpClient.disconnect();
        }


    }



}
