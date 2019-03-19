package com.shanzhaozhen.classroom.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

public class QRCodeUtils {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);

    private static final String CHARSET = "UTF-8";

    private static final String FORMAT_NAME = "JPG";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    public static BufferedImage createQRCodeImage(String content, String imgPath, boolean needCompress) throws WriterException, IOException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtils.insertImage(image, imgPath, needCompress);

        logger.info(ResourceUtils.getURL("classpath:").getPath());

//        图片生成
//        File outputfile = new File("D:\\ABC.jpg");
//        ImageIO.write(image, FORMAT_NAME, outputfile);

        return image;

    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws IOException {
        File file = new File(imgPath);
        if (!file.exists()) {
            logger.error("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    //  获取生成二维码的图片二进制
    public static byte[] encodeByte(String content, String imgPath, Boolean needCompress) throws Exception {
        return QRCodeUtils.encodeIO(content, imgPath, needCompress).toByteArray();
    }


    //获取生成二维码的图片流
    public static ByteArrayOutputStream encodeIO(String content, String imgPath, Boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createQRCodeImage(content, imgPath, needCompress);
        //创建储存图片二进制流的输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //将二进制数据写入ByteArrayOutputStream
        ImageIO.write(image, "jpg", baos);

        return baos;
    }

    //测试
    public static void main(String[] args) throws Exception {
        String text = "http://www.baidu.com";
        //QRCodeUtil.encode(text, "D:\\图片\\武僧2.jpg", "d:/MyWorkDoc", true);
        //String decode = QRCodeUtil.decode("D:\\MyWorkDoc\\32533141.jpg");
        ByteArrayOutputStream encodeIO = QRCodeUtils.encodeIO(text, "D:\\temp.jpg", true);
        //输入数组
        System.out.println(Arrays.toString(encodeIO.toByteArray()));

    }


}
