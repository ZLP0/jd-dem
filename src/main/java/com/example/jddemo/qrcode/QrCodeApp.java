package com.example.jddemo.qrcode;

import cn.hutool.core.codec.Base64;
import cn.hutool.extra.qrcode.QrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 程序员  by dell
 * time  2021-06-07
 **/

@Controller
public class QrCodeApp {


    /**
     * 前端 用image接收 <image src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsCAIAAAD2HxkiAAAFnklEQVR42u3asRWDQAxEQfpvGnfgBA6tpPm5/UBoLrrrllTaZQQShBKEkiCUIJQEoQShJAglCCVBKEEoCUIJQkkQShBKglCCUBKEEoSSIJQglAShBKEkCCUI//3XuM6975N/PveNqn47bzcghBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIX37okGfu+NuOs+q4GxBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYSV15q2Qdr2VB0vD0IIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBCuGPRnZb6R3YAQQgghhBBCuwEhhBBCCCGEdgNCCCGEEEII7QaEEEIIIYQQ2g0IIYQQwgmDviKrel+7ASGEEEIIIYR2A0IIIYQQQgjtBoQQQgghhBDaDQghhBBCCCGEEEIIIYTw4EO3eOaqf858I7sBIYQQQgghhHYDQgghhBBCCO0GhBBCCCGEENoNCCGEEEIIIYQQQggh3IiwY5lL6epZ/m5ACCGEEELotxBCCCGEEELotxBCCCGEEELotxBCCCGEEELotxBCCCGEWxAq/8BqcY1r3TIYAYQQQgghhBAKQgghhBBCCAUhhBBCCCGEghBCCCGEEEJBCGFfhFWf35Uo+L9/XwghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEMLK5fBUCZAyqWR+QQghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEcAvCjqszb6GrDp3MWVX9M4QQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCOHTz5BZ5nLMW/fMYxRCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIdyCMPOFXYhLOFY6HrIQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEQQ89oExIHY/RARsLIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBCORdixc+/bcZKmsfruqLUzDQghtHamAaG1Mw0IIbR2pgGhtTMNCCG0dqYBobUzDQghtHamAaG1Mw0IX78EVPUJM6+tbXvm5ZsDIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBC+OlSzuN9bs4u0536KBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIYV+E28pcncwDa9vhDiGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQurbmStSEC3FVb9QRP4QQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCGHl6lQtx7lpVD3zPAwh7wshhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEC5FmHn1LPPQuQQhhBBCCCGEghBCCCGEEEJBCCGEEEIIIYQQQgghhBBCCCGEEEIIIYSBCEM+cIuD4y4KQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEMKU5cicc9Uk512XgxBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIey60FXrPu8bVR0NEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhJWj7Hg1yULPPlYghBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIJZ05R4xAglCCUBKEEoSSIJQglAShBKEkCCUIJUEoQSgJQglCSRBKEEqCUIJQEoQShJIglCCUBKE0rh9bf9jGwMq5RQAAAABJRU5ErkJggg=="></image>
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getQrCode")
    @ResponseBody
    public String getBase64QrCodeString() throws IOException {

        //File generate = QrCodeUtil.generate("https://localhost:8080?name=zhangsan", 300, 300, FileUtil.file("d:/EVM2.jpg"));

        BufferedImage bufferedImage = QrCodeUtil.generate("https://www.baidu.com", 300, 300);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        String base64 = Base64.encode(stream.toByteArray());
        return "data:image/png;base64," +base64;
    }

}
