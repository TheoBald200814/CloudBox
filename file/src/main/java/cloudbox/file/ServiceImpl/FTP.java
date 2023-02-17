package cloudbox.file.ServiceImpl;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;


@Service
public class FTP {

    //服务器IP
    private static final String  FTP_ADDRESS = "47.115.208.235";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "ftptest";
    //密码
    private static final String FTP_PASSWORD = "qpddf_234";




    public void download_new(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {


        System.out.println("download_new");
        // 获取浏览器的信息
        String agent = request.getHeader("USER-AGENT");
        System.out.println("获取浏览器的信息");
        if (agent != null && agent.toLowerCase().indexOf("FIRE_FOX") > 0) {
            System.out.println("FIRE_FOX");
            //火狐浏览器自己会对URL进行一次URL转码所以区别处理
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
        } else if (agent.toLowerCase().indexOf("SAFARI") > 0) {
            System.out.println("SAFARI");
            //苹果浏览器需要用ISO 而且文件名得用UTF-8
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
        } else {
            System.out.println("OTHER");
            //其他的浏览器
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        }

        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_ADDRESS,FTP_PORT);

            ftp.setControlEncoding("GBK");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");

            ftp.login(FTP_USERNAME,FTP_PASSWORD);
            reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.out.println("连接失败");
            }else {
                System.out.println("连接成功");
                //开启被动模式
                ftp.enterLocalPassiveMode();
                //解决中文乱码问题
                ftp.setAutodetectUTF8(true);

                OutputStream outputStream= response.getOutputStream();

                ftp.changeWorkingDirectory("/ftp_01");
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

                InputStream   inputStream = ftp.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
                System.out.println("创建inputStream");
                if(inputStream!=null){
                    System.out.println("inputStream非空");

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf, 0, buf.length)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                    inputStream.close();

                    outputStream.flush();
                    outputStream.close();

                    ftp.logout();
                    ftp.disconnect();
                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public boolean delete(String fileUrl) throws Exception {

        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_ADDRESS,FTP_PORT);

            ftp.login(FTP_USERNAME,FTP_PASSWORD);

            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {

                ftp.disconnect();
                return false;
            }

            ftp.enterLocalPassiveMode();

            String fileName_iso = new String(fileUrl.getBytes("GBK"),"iso-8859-1");

            ftp.changeWorkingDirectory("/ftp_01");

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            System.out.println(fileUrl);

            boolean delete_result=ftp.deleteFile(fileUrl);

            return delete_result;

        } catch (SocketException e) {

            e.printStackTrace();
        }
        return false;
    }





}



