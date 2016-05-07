import java.util.*;
public class RE {
	
	public static boolean isInt(String value){
		char ch = '~';
		for(int i = 0 ; i < value.length() ; i++){
			ch = value.charAt(i);
			if(!Character.isDigit(ch)){
				System.out.println("@ isInt ch !isDigit >>> "+ch);
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDouble(String value){
		try{
			Double.parseDouble(value);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static String re(String value){
		String cp = "";
		char ch = value.charAt(0);
		if(Character.isDigit(ch)){
			if(isInt(value)) return "int_constant";
			if(isDouble(value)) return "float_constant";
			if(value.indexOf(ch) > 0) return "invalid_float_constant";
			else return "invalid_int_constant";
		}
		else if(isString(value)) return "string_constant";
		else{
			return "ID";
		}
	}
	private static boolean isString(String value){
		if(value.charAt(0)== '"' && value.charAt(value.length()-1)== '"')
		return true;	
		return false;
	}
	

}
