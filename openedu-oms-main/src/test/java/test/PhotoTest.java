package test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.UUID;

public class PhotoTest {

	public static void main(String[] args) {
		File file = new File(System.getProperty("java.io.tmpdir") + "/temp_" + UUID.randomUUID().toString() + ".zip");
		ZipOutputStream zos = null;
		FileInputStream fis = null;
		try {
			File template = new File("D:\\Temp\\photo.jpg");
			zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			FileInputStream fin = null;
			for (int i = 1; i < 30; i++) {
				String fileName = String.format("2016010110%02d_xxx.jpg", i);
				File photo = new File("D:\\Temp\\" + fileName);
				FileUtils.copyFile(template, photo);
				fin = new FileInputStream(photo);
				zos.putNextEntry(new ZipEntry(fileName.toString()));
				IOUtils.copy(fin, zos);
				IOUtils.closeQuietly(fin);
				photo.delete();
			}
			IOUtils.closeQuietly(zos);
			//下载
			String fileName = "photos.zip";
			OutputStream out = null;
			fis = new FileInputStream(file);
			out = new FileOutputStream(new File("D:\\Temp\\photos.zip"));
			IOUtils.copy(fis, out);
			out.flush();
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(fis);
		}
	}
}
