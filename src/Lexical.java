import java.util.*;
import java.io.*;
import java.io.*;
import javax.swing.*;
public class Lexical {
	public Tokens[] tokenSet;
	private String filename = null;
	private CpVp cpvp = null;
	private Scanner sourceCode;
	private PrintWriter tokensfile;
	private char[] chars;
	private int line_no;
	
	
	
	public Lexical(String filename){
		line_no =1;
		this.filename = filename;
		cpvp =  CpVp.getCPVP();
		
		
		
		try {
			sourceCode = new Scanner(new File(filename));
			tokensfile = new PrintWriter(filename.substring(0, filename.indexOf('.'))+".TokSet");
			chars = new char[countChar()];
		} catch (FileNotFoundException e) {
			String error = "";
			error += "Error 102 :\n"+
			"Lexical Analyzer Failed \n"+
			filename + " not found";
			JOptionPane.showMessageDialog(null,error );
		}
		try {
			charOperation();
		} catch (Exception e) {
			System.out.println("Lexical Failed bcoz "+e.getMessage());
		}
	
	}
	
	
	public Tokens makeTokens(String value){
		value = value.trim();
		Tokens t = null;
		if(value.equals("")){
			return t;
		}
		//System.out.println("value >> "+value);
			
		for(int j =0 ; j<cpvp.vp.length; j++){
			//System.out.println(value + " == "+ cpvp.vp[j]);
			if(value.equals(cpvp.vp[j])){
				t = new Tokens(value,cpvp.cp[j],line_no);
				tokensfile.println(t.toString());
				return t;
			}
		}
			t = new Tokens(value,RE.re(value),line_no);
			tokensfile.println(t.toString());
	

		return t;
	}
	public Tokens makeErrorTokens(String error){
		Tokens t = null;
		t = new Tokens(error,"ERROR",line_no);
		tokensfile.println();
		return t;
	}
	
	
	public int countChar() throws FileNotFoundException{
		Scanner in = new Scanner(new File(filename));
		int noOfchars = 0;
		while(in.hasNextLine()){
			noOfchars += in.nextLine().length()+1;
		}
		in.close();
		return noOfchars;
	}
	//todo
	public char[] toChars(){
		int index = 0;
		while(sourceCode.hasNextLine()){
			String line = sourceCode.nextLine();
			for(int i =0 ; i < line.length(); i++){
				chars[index] = line.charAt(i);
				index++;
			}
			chars[index] = '~';
			index++;
			
		}
		/*for(int i =0 ; i<chars.length ; i++){
			if(chars[i] == (char)2000)
				System.out.println("char( "+i+" , "+ "newline"+")");
			else if(chars[i] == ' ')
				System.out.println("char( "+i+" , "+ "space"+")");
			else
				System.out.println("char( "+i+" , "+ chars[i]+")");
		}*/
		
		return chars;
	}
	public int countLi(String fn){
		int i =1;
		Scanner count = null;
		//System.out.println("File Name of fn == "+fn);
		try {
			count = new Scanner(new File(fn));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("At Lexical - > TokInd - > try Block where fn ="+fn);
			e.printStackTrace();
		}
		System.out.println(count.nextLine());
		while(count.hasNextLine()){
			//System.out.println("value of i = "+i);
			i++;
			count.nextLine();
		}
		count.close();
		return i;
	}
	
	public void iniTokset(){
		tokenSet = new Tokens[countLi(filename.substring(0, filename.indexOf('.'))+".TokSet")];
		//System.out.println("countLi >>"+countLi(filename.substring(0, filename.indexOf('.'))+".TokSet"));
		
	}
	
	public void charOperation() throws Exception {
		
		char[] ch = toChars();
		String temp = "";
		
		for(int i = 0 ; i<ch.length ; i++){
			if(ch[i] == '~'){
				line_no++;
			}
			/*
			 * 	To make Token Of String Literl
			 */
			if(ch[i] == '"'){
				do{
					
					
					if(i > ch.length){
						System.err.println("Exception on token string");
						makeErrorTokens("String Litteral May Not Closed");
						break;
					}
					temp += ch[i];
					i++;
					
				}while(ch[i] != '"' || ch[i-1] == '\\');
				temp += ch[i];
				i++;
				makeTokens(temp);
				temp = "";
			}
			outer:
			if(cpvp.isWordBreaker(ch[i])){
				if(ch[i]=='.' && Character.isDigit(ch[i+1])){
					temp+=ch[i];
					i++;
					break outer;
				}
				makeTokens(temp);
				temp = "" + ch[i];
				switch(ch[i]){
				case '~':
				case ' ':
				case '\t':
					temp = "";
					break;
					
					
				case '+':
				case '-':
					if(ch[i+1] == ch[i]){
						i++;		// for ++ --
						temp += ch[i];
						break;
					}
				case '/':
				case '*':
				case '%':
				case '=':
				case '>':
				case '<':
					if(ch[i+1] == '='){
						i++;			// for <= >= == += -=  /= *=
						temp += ch[i];
						break;
					}
				default:
					
				}
				makeTokens(temp);
				temp = "";
			}
			else{
				temp += ch[i];
			 
			}
		}
		
	}
	public void close(){
		tokensfile.close();
		sourceCode.close();
	}
	public Tokens[] getTokens(){
		iniTokset();
		Scanner readToken = null;
		try {
			readToken = new Scanner(new File(filename.substring(0, filename.indexOf('.'))+".TokSet"));
		} catch (FileNotFoundException e) {
			System.out.println(filename+".TokSet not found or missing");
			e.printStackTrace();
			readToken.close();
			return tokenSet;
		}
		
		/// Initialize each token set
		int i =0;
		String vp;
		String cp;
		String lno;
		int lNoInt =0;
		Scanner lineSc = null;
		System.out.println(i<tokenSet.length);
		System.out.println(i+"<"+tokenSet.length);
		while(readToken.hasNextLine()&& i<tokenSet.length){
			lineSc = new Scanner(readToken.nextLine());
			lineSc.useDelimiter("~");
			vp = lineSc.next().trim();
			cp = lineSc.next().trim();
			lno = lineSc.next().trim();
			try{
			lNoInt = Integer.parseInt(lno);
			}
			catch(Exception e){
				System.out.println("Error in --> Lexical --> getToken --> catch");
				System.out.println("vp == "+vp+"cp == "+cp+"lno == "+lno);
			}
			tokenSet[i] = new Tokens(vp,cp,lNoInt);
			i++;
		}
		readToken.close();
		return tokenSet;
	}
	
	public void showAllTokens(){
		for(int i =0 ; i<tokenSet.length ; i++)
			tokenSet[i].println(); 
		
	}
	
	
}
