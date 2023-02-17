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
    private static final String FTP_BASEPATH = "/home/data/ftptest";
    // FTP服务器基础路径


    /**
     * 文件上传方法
     * @param file 文件数据
     * @param remotePath 上传路径
     * @param fileName 上传名
     * @return 上传成功，返回true；上传失败，返回false
     */
    public boolean uploadFile(MultipartFile file, String remotePath, String fileName) {

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
                ftpClient.makeDirectory(remotePath);
                ftpClient.changeWorkingDirectory(FTP_BASEPATH + remotePath);
            }

            InputStream inputStream = file.getInputStream();
            //文件转输入流
            result = ftpClient.storeFile(fileName, inputStream);
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


    public void downloadFileFromFTP(String fileName, String remotePath, HttpServletResponse response) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            //建立FTP服务器连接
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 设置工作目录
            ftpClient.changeWorkingDirectory(remotePath);

            // 下载文件并输出到前端
            String remoteFile = fileName;
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

}
