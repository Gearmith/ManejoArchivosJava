/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package john.manejoarchivos;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

/**
 *
 * @author johnferneymontoyaramirez
 */
public class Crud {
	void crearRegistro(String nombre, String numero) {
		try {
			String nameNumberString;
			String name;
			String number;


			File file = new File("friends.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			RandomAccessFile raf = new RandomAccessFile(file, "rw");

			boolean found = false;

			while (raf.getFilePointer() < raf.length()) {
				nameNumberString = raf.readLine();

				String [] lineSplit = nameNumberString.split("!");

				name = lineSplit[0];
				number = lineSplit[1];
				
				if (name.equals(nombre) || number.equals(numero)) {
					found = true;
					break;
				}
			}
			
			if (found == false) {
				nameNumberString = nombre + "!" + numero;
				
				raf.writeBytes(nameNumberString);
				raf.writeBytes(System.lineSeparator());
				JOptionPane.showMessageDialog(null, "Anadido");
				raf.close();
			} else {
				raf.close();
			}
		} catch (IOException | NumberFormatException ioe) {
			System.out.println(ioe);
		}	
	}
	
	void verContactos () {
		try {
			String nameNumberString;
			String name;
			String number;
			
			File file = new File("friends.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			
			while (raf.getFilePointer() < raf.length()) {
				nameNumberString = raf.readLine();
				String [] lineSplit = nameNumberString.split("!");
				name = lineSplit[0];
				number = lineSplit[1];
				
				JOptionPane.showMessageDialog(null, "Nombre: " + name + "\nNumero: " + number + "\n");
			}
		} catch (IOException | NumberFormatException ioe) {
			System.out.println(ioe);
		}
	}
	
	void actualizarRegistro(String nombre, String numero) {
		try {
			String nameNumberString;
			String name;
			String number;
			int index;
			
			File file = new File("friends.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			boolean found = false;
			
			while (raf.getFilePointer() < raf.length()) {
				nameNumberString = raf.readLine();
				String [] lineSplit = nameNumberString.split("!");
				name = lineSplit[0];
				number = lineSplit[1];
				
				if (name.equals(nombre) || number.equals(numero)) {
					found = true;
					break;
				}
			}
			
			if (found == true) {
				File tmpFile = new File("temp.txt");
				try (RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw")) {
					raf.seek(0);
					
					while(raf.getFilePointer() < raf.length()) {
						nameNumberString = raf.readLine();
						
						index = nameNumberString.indexOf("!");
						name = nameNumberString.substring(0, index);
						
						if (name.equals(nombre)) {
							nameNumberString = name + "!" + numero;
						}
						
						tmpraf.writeBytes(nameNumberString);
						tmpraf.writeBytes(System.lineSeparator());
					}
					
					raf.seek(0);
					tmpraf.seek(0);
					
					while (tmpraf.getFilePointer() < tmpraf.length()) {
						raf.writeBytes(tmpraf.readLine());
						raf.writeBytes(System.lineSeparator());
					}
					
					raf.setLength(tmpraf.length());
				}
				raf.close();
				tmpFile.delete();
				JOptionPane.showMessageDialog(null, "Actualizado");
			}
		} catch (IOException | NumberFormatException ioe) {
			System.out.println(ioe);
		}
	}
	
	void eliminarRegistro(String nombre) {
		try {
			String nameNumberString;
			String name;
			int index;
			
			File file = new File("friends.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			boolean found = false;
			
			while (raf.getFilePointer() < raf.length()) {
				nameNumberString = raf.readLine();
				String[] lineSplit = nameNumberString.split("!");
				
				name = lineSplit[0];
				
				if (name.equals(nombre)) {
					found = true;
					break;
				}
			}
			
			if (found == true) {
				File tmpFile = new File("temp.txt");
				try (RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw")) {
					raf.seek(0);
					
					while (raf.getFilePointer() < raf.length()) {
						nameNumberString = raf.readLine();
						index = nameNumberString.indexOf("!");
						name = nameNumberString.substring(0, index);
						
						if (name.equals(nombre)) {
							continue;
						}
						
						tmpraf.writeBytes(nameNumberString);
						tmpraf.writeBytes(System.lineSeparator());
					}
					
					raf.seek(0);
					tmpraf.seek(0);
					
					while (tmpraf.getFilePointer() < tmpraf.length()) {
						raf.writeBytes(tmpraf.readLine());
						raf.writeBytes(System.lineSeparator());
					}
					
					raf.setLength(tmpraf.length());
				}
				raf.close();
				tmpFile.delete();
				
				JOptionPane.showMessageDialog(null, "Eliminado");
			} else {
				raf.close();
				JOptionPane.showMessageDialog(null, nombre + " no existe");
			}
		} catch (IOException | NumberFormatException ioe) {
			System.out.println(ioe);
		}
	}
}

