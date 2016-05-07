import java.io.*;
import java.util.*;
import javax.swing.*;
public class CpVp {
	private static CpVp ins = null;
	
	
	String[] vp;
	String[] cp;
	char[] wBR;
	int cpvpLines = 0;
	int wBrLines = 0;
	private CpVp(String CPVP, String WBR){
		Scanner countLine = null;
		Scanner fileIn = null;
		Scanner fileWBr = null;
		try {
			fileIn = new Scanner(new File(CPVP));
			countLine = new Scanner(new File(CPVP));
			fileWBr = new Scanner(new File(WBR));
		} catch (FileNotFoundException e) {
			String error = "";
			error += "Error 101 :\n"+
			CPVP + " is mising";
			JOptionPane.showMessageDialog(null,error );
			
		}
		
		
		/*
		 * 	COUNTING NO OF LINES IN CPVP
		 */
		while( countLine.hasNextLine() ){
			countLine.nextLine();
			cpvpLines++;
		}
		vp = new String[cpvpLines];
		cp = new String[cpvpLines];
		
		for(int i=0;fileIn.hasNextLine();i++){
			Scanner words = new Scanner(fileIn.nextLine());
			vp[i] = words.next().trim();
			cp[i] = words.next().trim();
			//System.out.println("CpVp fileIn.nextLine = "+vp[i]+"  " +cp[i]);
		}
		Scanner countWBr = null;
		try {
			countWBr = new Scanner(new File(WBR));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while( countWBr.hasNextLine() ){
			countWBr.nextLine();
			wBrLines++;
		}
		wBR = new char[wBrLines];
		
		for(int i=0;fileIn.hasNextLine();i++){
			Scanner words = new Scanner(fileIn.nextLine());
			vp[i] = words.next().trim();
			cp[i] = words.next().trim();
		}
		for(int i = 0 ; fileWBr.hasNextLine(); i++){
			try{
			wBR[i] = fileWBr.nextLine().charAt(0);
			}
			catch(Exception e){
				System.out.println(e.getMessage()+"\n at :"+i);
			}
		}
		
		
	}
	
	private CpVp(){
		this("CPVP.computer","WBR.computer");	
	}
	
	
	public boolean isWordBreaker(char ch){
		if(ch == ' ' || ch == '\t'){
			return true;
		}
		for(int i = 0 ; i<wBR.length ; i++){
			//System.out.println(ch + " == " + wBR[i] + " >> "+ (ch == wBR[i]));
			if(ch == wBR[i]){
				return true;
			}
		}
		return false; 
	}
	
	
	


	
		

	public static CpVp getCPVP(){
		if(ins ==null){
			ins = new CpVp();
		}
		return ins;
	}
	
	/*public static void main(String[] args){
		CpVp t = CpVp.getTokens();
		
		for(int i = 0 ; i<t.cp.length ; i++){
			System.out.println("( "+t.vp[i]+" , "+t.cp[i]+" , "+i+" )");
		}
	}*/
}
