package com.hzxc.chz.server.utils;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.osgl.util.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;

/**
 * create by chz on 2018/2/28
 */
public class ShareQrUtil {
    private static Logger logger = LoggerFactory.getLogger(ShareQrUtil.class);
    private static String bg;
    private static String man;
    private static String share;
    private static String fontFile;

    private static String getBg() {
        if (bg == null) {
            bg = ClassUtils.getDefaultClassLoader().getResource("static/image/bg.png").getPath();
        }
        logger.info("bg file:{}",bg);
        return bg;
    }

    private static String getPlay() {
        if (man == null) {
            man = ClassUtils.getDefaultClassLoader().getResource("static/image/play.png").getPath();
        }
        logger.info("man file:{}",man);
        return man;
    }

    private static String getShare() {
        if (share == null) {
            share = ClassUtils.getDefaultClassLoader().getResource("static/image/share.png").getPath();
        }
        logger.info("man file:{}",share);
        return share;
    }

    private static String getFontFile(){
        if(fontFile == null){
            fontFile = ClassUtils.getDefaultClassLoader().getResource("static/font/PingFangRegular.ttf").getPath();
        }
        logger.info("fontFile file:{}",fontFile);
        return fontFile;
    }


    public static void getShareImage(String coverFile, String qrFile, String text,String duration, String outFile) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(coverFile);
        op.resize(null, 388);//变尺寸
        op.blur(50d, 8d);//模糊
        op.resize(690, 388, "!");//强制变尺寸

        op.compose("over").addImage(coverFile);//覆盖图片

        op.gravity("north").geometry(null, 388);
        op.composite();
        op.addImage(outFile + "~temp.jpg");
        ConvertCmd convert = new ConvertCmd();
        convert.run(op);


        op = new IMOperation();
        op.addImage(getBg());
        op.compose("over").addImage(outFile + "~temp.jpg");//覆盖图片
        op.geometry(null, null, 30, 30);
        op.composite();

        op.compose("over").addImage(getPlay());//覆盖图片
        op.gravity("north").geometry(null, null, 0, 377);
        op.composite();

        op.compose("over").addImage(qrFile);//覆盖图片
        op.gravity("northwest").geometry(216, 216, 26, 611);
        op.composite();
        String s;
        if(text.length() <= 22){
            s = text;
        }else{
            StringBuilder textBuilder = new StringBuilder(text.length()+3);
            textBuilder.append(text);
            if(textBuilder.length()>42){
                textBuilder.replace(41,textBuilder.length(),"...");
            }
            textBuilder.insert(21,"\n");
            s = textBuilder.toString();
        }

        op.font(getFontFile()).pointsize(32).fill("black")
                .draw("text 35,467 \"" + s + "\"");

        if(S.isNotBlank(duration)){
            op.fill("#323231").draw("roundRectangle 627,382 712,404 4,4");
            op.fill("white").pointsize(18).draw("text 632,380 '"+duration+"'");
        }

        op.addImage(outFile);
        convert = new ConvertCmd();
        convert.run(op);
        new File(outFile + "~temp.jpg").delete();
    }

    // 每日收入分享
    public static void getEarnShareImg(String userid, String uname, int coinCount, int rmbCount, String retFile) throws Exception{
        IMOperation op = new IMOperation();
        op.addImage(getBg());

        op.compose("over").addImage(getPlay());//覆盖图片
        op.gravity("north").geometry(null, null, 0, 277);
        op.composite();

        op.compose("over").addImage(getShare());//覆盖图片
        op.gravity("northwest").geometry(216, 216, 26, 611);
        op.composite();

        String text = "看视频也能賺钱，\n我今天收入金币" + coinCount + "，\n人民币收入" + rmbCount + "元";

        op.font(getFontFile()).pointsize(32).fill("black")
                .draw("text 135,427 \"" + text + "\"");

        if(S.isNotBlank(userid)){
            // 范围填充颜色
//            op.fill("#323231").draw("roundRectangle 627,382 712,404 4,4");
            op.fill("red").pointsize(28).draw("text 632,380 '"+userid+"'");
        }

        op.addImage(retFile);
        ConvertCmd convert = new ConvertCmd();

        convert.run(op);
    }

    // 师徒收入分享
    public static void getMasterShareImg() {

    }

}
