package com.yzsoft.yxxt.mobile.service.impl;

import com.swetake.util.Qrcode;
import com.yzsoft.yxxt.mobile.service.QrcodeService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class QrcodeServiceImpl implements QrcodeService {

	public String createQRCode(String data) {
		try {
			if (StringUtils.isBlank(data)) {
				return null;
			}
			data = addCheck(data);
			BufferedImage bufImg = null;
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('L');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			// qrcodeHandler.setQrcodeVersion(size);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = data.getBytes("utf-8");
			boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
			// 图片尺寸
			int imgSize = codeOut.length * 3 + 3;
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
			}
			gs.dispose();
			bufImg.flush();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufImg, "jpg", baos);
			byte[] buf = baos.toByteArray();
			String base64 = Base64.encodeBase64String(buf);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成二维码
	 */
	private String addCheck(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		if (data.length() > 500) {
			data = data.substring(0, 500);
		}
//		data += "#" + EncryptUtil.encode(data + "#6B6BDF4328D069FFD14E8AA8EF0244FA").toUpperCase();
		return data;
	}
}
