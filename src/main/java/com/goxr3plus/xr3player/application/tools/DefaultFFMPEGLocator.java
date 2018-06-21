/*
 * JAVE - A Java Audio/Video Encoder (based on FFMPEG) Copyright (C) 2008-2009 Carlo Pelliccia (www.sauronsoftware.it) This program is free software:
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.com.goxr3plus.xr3player.application.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;

import it.sauronsoftware.jave.FFMPEGLocator;

/**
 * The default ffmpeg executable locator, which exports on disk the ffmpeg executable bundled with the library distributions. It should work both for
 * windows and many linux distributions. If it doesn't, try compiling your own ffmpeg executable and plug it in JAVE with a custom
 * {@link FFMPEGLocator}.
 *
 * @author Carlo Pelliccia
 */
public class DefaultFFMPEGLocator extends FFMPEGLocator {
	
	/**
	 * Trace the version of the bundled ffmpeg executable. It's a counter: every time the bundled ffmpeg change it is incremented by 1.
	 */
	private static final int MY_EXE_VERSION = 2;
	
	/**
	 * Keep a LocalDate of current Jave2 Version
	 */
	private static final LocalDate local_date = LocalDate.of(2018, 06, 21);
	
	/**
	 * The ffmpeg executable file path.
	 */
	private final String path;
	
	/**
	 * It builds the default FFMPEGLocator, exporting the ffmpeg executable on a temp file.
	 */
	public DefaultFFMPEGLocator() {
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");
		boolean isMac = os.contains("mac");
		
		// Dir Folder
		File dirFolder = new File(System.getProperty("java.io.tmpdir"), "jave-" + MY_EXE_VERSION);
		if (!dirFolder.exists())
			dirFolder.mkdirs();
		
		// -----------------ffmpeg executable export on disk.-----------------------------
		String suffix = isWindows ? ".exe" : "-osx";
		String arch = System.getProperty("os.arch");
		
		//File
		File ffmpegFile = new File(dirFolder, "ffmpeg-" + arch + suffix);
		
		//Check the version of existing .exe file
		if (ffmpegFile.exists()) {
			BasicFileAttributes attr;
			try {
				attr = Files.readAttributes(ffmpegFile.toPath(), BasicFileAttributes.class);
				System.out.println("Creation date: " + attr.creationTime());
			} catch (IOException e) {
				System.out.println("oops error! " + e.getMessage());
			}
		}
		
		//If the file doesn't already exists
		if (!ffmpegFile.exists())
			copyFile("ffmpeg-" + arch + suffix, ffmpegFile);
		
		// Need a chmod?
		if (!isWindows) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(new String[]{ "/bin/chmod" , "755" , ffmpegFile.getAbsolutePath() });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Ok.
		this.path = ffmpegFile.getAbsolutePath();
	}
	
	@Override
	protected String getFFMPEGExecutablePath() {
		return path;
	}
	
	/**
	 * Copies a file bundled in the package to the supplied destination.
	 *
	 * @param path
	 *            The name of the bundled file.
	 * @param dest
	 *            The destination.
	 * @throws RuntimeException
	 *             If an unexpected error occurs.
	 */
	private void copyFile(String path , File dest) throws RuntimeException {
		copy(getClass().getResourceAsStream("native/" + path), dest.getAbsolutePath());
	}
	
	/**
	 * Copy a file from source to destination.
	 *
	 * @param source
	 *            The name of the bundled file.
	 * @param destination
	 *            the destination
	 * @return True if succeeded , False if not
	 */
	private boolean copy(InputStream source , String destination) {
		boolean success = true;
		
		try {
			Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			//LOG.warn("Cannot write file " + destination, ex)
			ex.printStackTrace();
			success = false;
		}
		
		return success;
	}
}
