package com.airo;

import java.io.*;
import java.util.LinkedList;

public class IOX {
	private BufferedReader br;
	private BufferedWriter bw;
	private File f;
	private LinkedList<String> buffer;
	private int fs;

	/**
	 * 打开一个文件
	 * @param path 文件路径
	 */
	public void Open(String path){
		f = new File(path);
		CheckFile();
		ResetBuffer();
		ResetReader(f);
		ResetWriter(f);
	}

	/**
	 * 检查文件是否存在，否则新建一个文件
	 * @return
	 */
	public boolean CheckFile(){
		boolean e=f.exists();
		if(!e){
			CreateFile();
		}
		return e;
	}

	/**
	 * 新建文件
	 */
	public void CreateFile(){
		try {
			f.createNewFile();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 关闭io流
	 */
	public void Dispose(){
		try{
			br.close();
			bw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 从头覆写文件
	 * @param content 内容
	 */
	public void OverWrite(String content){
		Write(content,false);
	}

	/**
	 * 从特定行覆写文件
	 * @param content 内容
	 * @param line 行数
	 */
	public void OverWrite(String content,int line){
		buffer.set(line,content);
		FlushOutput();
	}

	/**
	 * 从文件末尾追加内容
	 * @param content 内容
	 */
	public void Write(String content){
		Write(content,true);
	}

	/**
	 * 以特定模式写入内容
	 * @param content 内容
	 * @param append 是否追加
	 */
	public void Write(String content,boolean append){
		try {
			ResetWriter(f,append);
			bw.write(content);
			bw.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件所有内容
	 * @return 文件内容
	 */
	public String ReadAll(){
		FlushInput();
		String r="";
		for(String line:buffer){
			r+=line+"\n";
		}
		return r;
	}

	/**
	 * 读取文件内容
	 * @param line 行数
	 * @return
	 */
	public String Read(int line){
		FlushInput();
		if(line>fs){
			return "[ERROR]no content in this line";
		}
		return buffer.get(line);
	}

	/**
	 * 刷新文件内容到缓存
	 */
	public void FlushInput(){
		try {
			ResetReader(f);
			ClearBuffer();
			int size=0;
			while(br.ready()){
				buffer.add(br.readLine());
				size++;
			}
			fs=size;
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 刷新缓存到文件
	 */
	public void FlushOutput(){
		try {
			ResetWriter(f,false);
			for(String line:buffer){
				bw.write("\n"+line);
			}
			bw.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 重置读取器
	 * @param f 文件
	 */
	public void ResetReader(File f){
		try {
			br = new BufferedReader(new FileReader(f));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 重置写入器
	 * @param f 文件
	 */
	public void ResetWriter(File f){
		ResetWriter(f,true);
	}

	/**
	 * 以特定模式重置写入器
	 * @param f 文件
	 * @param append 是否追加
	 */
	public void ResetWriter(File f,boolean append){
		try {
			bw = new BufferedWriter(new FileWriter(f,append));
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 重置缓存
	 */
	public void ResetBuffer(){
		buffer = new LinkedList<>();
	}

	/**
	 * 清除缓存
	 */
	public void ClearBuffer(){
		buffer.clear();
	}

	//各种封装函数
	public File GetFile(){return f;}
	public int GetFileSize(){return fs;}
	public BufferedReader GetReader(){return br;}
	public BufferedWriter GetWriter(){return bw;}
	public LinkedList<String> GetBuffer(){return buffer;}
}
