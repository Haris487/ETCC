import java.util.*;
import java.io.*;
import java.io.*;
import javax.swing.*;

public class SyntaxTree {
	String Error = "Sementic Completed\n";
	Tokens[] tokenSet = null;
	int ind = 0;
	int I = 0;
	boolean isCorrect = false;
	Stack<Integer> st = new Stack<Integer>();
	ArrayList<Sym> symbolTable = new ArrayList<Sym>();
	int scope = 0;
	public SyntaxTree(Tokens[] tokenSet){
		this.tokenSet = tokenSet;
		node("<syntax tree>");
		// <Syntax Tree> -- > <importStatement><mainFunction><classDecelaration> 
		if(importStatements() && syntaxTreePrime()){
			isCorrect = true;
		}
		else{
			
			// Error Massage . . . . . .
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("Syntax Error " );
			System.out.println("Error "+ind);
			System.out.println("At Line " + tokenSet[ind].lineNo);
			System.out.println(tokenSet[ind].vp +" is Syntatically Wrong");
			System.out.println(tokenSet[ind].cp+" is the Class Part ");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
		}
	}
	
	public boolean importStatements(){
		String n;
		node("<importStatement>");
		if(tokenSet[ind].cp.equals("Import_export")){
			System.out.println(tab()+tokenSet[ind].cp);
			ind++;
			if(tokenSet[ind].cp.equals("ID")){
				if(importcheaker(tokenSet[ind].vp))  err(tokenSet[ind].vp + "no such file present to import at "+tokenSet[ind].lineNo + "\n" );
				System.out.println(tab()+tokenSet[ind].cp);
				ind++;
				if(tokenSet[ind].cp.equals(";")){
					System.out.println(tab()+tokenSet[ind].cp);
					ind++;
					if(importStatements()){
						I--;
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
			else{
				System.out.println();
				return false;
			}
		}
		else{
			System.out.println(tab()+"null");
			I--;
			return true;
		}
		
		
	}
	public boolean mainFunction(){
		return true;
	}
	public boolean classDeclaration(){
		node("<classDeclaration>");
		if(tokenSet[ind].cp.equals("ACC-MOD")){
			node();
			if(static_non()){
				if(tokenSet[ind].cp.equals("class")){
					node();
					if(tokenSet[ind].cp.equals("ID")){
						node();
						if(tokenSet[ind].cp.equals("{")){
							scopein();
							node();
							if(class_body()){
								if(tokenSet[ind].cp.equals("}")){
									scopeout();
									node();
									I--;
									return true;
								}
								else{
									I--;
									return false;
								}
							}
							else{
								I--;
								return false;
							}
						}
						else{
							I--;
							return false;
						}
					}
					else{
						I--;
						return false;
					}
				}
				else{
					I--;
					return false;
				}
			}
			else{
				I--;
				return false;
			}
			
		}
		else{
			I--;
			return false;
		}
	}
	public boolean static_non(){
		node("<static_non>");
		if(tokenSet[ind].cp.equals("static")){
			node();
			I--;
			return true;
		}
		else if(tokenSet[ind].cp.equals("virtual")){
			node();
			I--;
			return true;
		}
		else{
			nul();
			I--;
			return true;
		}
	}
	public boolean syntaxTreePrime(){
		node("<syntaxTreePrime>");
		//Left Factored
		if(mainFunction() && classDeclaration()|| classDeclaration() && mainFunction()){
			I--;
			return true;
		}
		else return false;
	}
	public boolean class_body(){
		node("<class_body>");
		if(class_mst()){ 
			I--;
			return true;
		}
		else{
			return false;
		}
	}
	public boolean class_sst(){
		node("<class_sst>");
		if(int_dec()){
			I--;
			return true;
		}
		else{
			I--;
			return false;
		}
	}
	public boolean class_mst(){
		node("<class_mst>");
		if(class_sst()){
			if(class_mst()){
				I--;
				return true;
			}
			else{
				return false;
			}
		}
		else{
			nul();
			I--;
			return true;
		}
	}
	public boolean parameter_declear(){
		node("<parameter_declear>");
		if(tokenSet[ind].cp.equals("DT")||tokenSet[ind].cp.equals("ID")){
			node();
			if(tokenSet[ind].cp.equals("ID")){
				node();
				if(list_parameter_declear()){
					I--;
					return true;
				}
				else{
					I--;
					return false;
				}
			}
			else{
				I--;
				return false;
			}
			
		}
		else{
			nul();
			I--;
			return true;
		}
		
	}
	public boolean list_parameter_declear(){
		node("<list_parameter_declear>");
		if(tokenSet[ind].cp.equals(",")){
			node();
			if(parameter_declear()){
				I--;
				return true;
			}
			else{
				I--;
				return false;
			}
			
		}
		else{
			nul();
			I--;
			return true;
		}
	}
	public boolean int_dec(){
		String n = null ; 
		String t  = null;
		node("<int_dec>");
		if(tokenSet[ind].cp.equals("DT")){
			t = tokenSet[ind].vp; 
			node();
			if(tokenSet[ind].cp.equals("ID")){
				n = tokenSet[ind].vp;
				node();
				if(list_int_dec(t,n)){
					I--;
					return true;
				}
				else{
					I--;
					return false;
				}
				
				
			}
			else{
				I--;
				return false;
			}
		}
		// Object Decleration e.g Token t  = new Token(parameters);
		else if(tokenSet[ind].cp.equals("ID")){
			t = tokenSet[ind].vp;
			/*
			 * 
			 *  Class LOOK UP Function is missing TODO
			 * 
			 * 
			 */
			
			node();
			if(tokenSet[ind].cp.equals("ID")){
				n = tokenSet[ind].vp;
				node();
				if(list_int_dec(t,n)){
					I--;
					return true;
				}
				else{
					return false;
				}
			}
			else{
				I--;
				return false;
			}
		}
		else{
			I--;
			return false;
		}
	}
	public boolean list_int_dec(String t , String n){
		int i;
		node("<list_int_dec>");
		if(tokenSet[ind].cp.equals(",")){
			if(lookup(n) == null){
				insert(n,t);
			}
			else{
				System.out.println("ERROR . . ! duplicate local "+t+" "+ n );
			}
			node();
			if(tokenSet[ind].cp.equals("ID")){
				n = tokenSet[ind].vp;
				node();
				if(list_int_dec(t,n)){
					I--;
					return true;
				}
				else{
					I--;
					return false;
				}
			}
			else{
				I--;
				return false;
			}
			
		}
		else if(tokenSet[ind].cp.equals("ASS_OP")){
			/*
			 * 
			 * TODO typecasting function is missing
			 * 
			 * 
			 * 
			 */
			node();
			if(list_int_dec_prime(t,n)){
				I--;
				return true;
				
			}
			else{
				I--;
				return false;
			}
			
		}
		else if(tokenSet[ind].cp.equals(";")){
			if(lookup(n) == null){
				insert(n,t);
			}
			else{
				System.out.println("ERROR . . ! duplicate lacal "+t+" "+ n );
			}
			node();
			I--;
			return true;
			
		}
		else{
			I--;
			return false;
		}
	}
	
	public boolean list_int_dec_prime(String t , String n){
		node("<list_int_dec_prime>");
		if(tokenSet[ind].cp.equals("ID")){
			String t1 = lookup(n);
			node();
			if(list_int_dec(t , n)){
				I--;
				return true;
			}
			else{
				I--;
				return false;
			}
		}
		else if(constant()){
			if(list_int_dec(t , n)){
				I--;
				return true;
			}
		}
		return false;
	}
	public boolean constant(){
		node("<constamnt>");
		if(tokenSet[ind].cp.equals("int_constant")){
			node();
			I--;
			return true;
		}
		else if(tokenSet[ind].cp.equals("float_constant")){
			node();
			I--;
			return true;
		}
		else if(tokenSet[ind].cp.equals("string_constant")){
			node();
			I--;
			return true;
		}
		else if(tokenSet[ind].cp.equals("char_constant")){
			node();
			I--;
			return true;
		}
		else if(constr_call()){
			I--;
			return true;
		}
		return false;
		
	}
	public boolean constr_call(){
		node("<constr_call>");
		if(tokenSet[ind].cp.equals("new")){
			node();
			if(tokenSet[ind].cp.equals("ID")){
				node();
				if(tokenSet[ind].cp.equals("(")){
					node();
					if(parameter_pass()){
						if(tokenSet[ind].cp.equals(")")){
							node();
							I--;
							return true;
						}
						else{
							I--;
							return false;
						}
					}
					else{
						I--;
						return false;
					}
				}
				else{
					I--;
					return false;
				}
				
			}
			else{
				I--;
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	public boolean parameter_pass(){
		node("< parameters_pass()>");
		if(tokenSet[ind].cp.equals("ID")){
			node();
			if(list_parameter_pass()){
				I--;
				return true;
			}
			else{
				I--;
				return false;
			}
		}
		else{
			nul();
			I--;
			return true;
		}
	}
	public boolean list_parameter_pass(){
		node("<list_parameters_pass>");
		if(tokenSet[ind].cp.equals("ID")){
			node();
			if(parameter_pass()){
				I--;
				return true;
			}
			else{
				I--;
				return false;
			}
		}
		else{
			I--;
			return false;
		}
	}
	
	
	
	
	//Non CFG Function
	public String tab(){
		String align = "";
		for(int i=0 ; i<I ; i++){
			align += "\t";
		}
		return align;
	}
	public void node(){
		System.out.println(tab()+tokenSet[ind].cp+"("+tokenSet[ind].vp+")");
		ind++;
	}
	public void node(String arg){
		System.out.println(tab()+arg);
		I++;
	}
	public void nul(){
		System.out.println(tab()+"Null");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	 *		_      ______                   _______                 ______
	 *     /      |           |\        /|  |        |\        /       |
	 *    |       |           | \      / |  |        | \      /        |
	 *     \_     |______     |  \    /  |  |_______ |  \    /         |
	 *       |    |           |   \  /   |  |        |   \  /          |
	 *      _/    |______     |    \/    |  |_______ |    \/           |
	 * 
	 * 
	 */
	
	void scopein(){
		scope++;
		st.push(scope);
	}
	void scopeout(){
		scope--;
		st.pop();
	}
	void insert(String n , String t ){
		Sym ls = new Sym(n,t,Scope());
		symbolTable.add(ls);
		System.out.println("symbol table updated ("+ls.name + "   " + ls.type + "  " + ls.scope+")");
		
	}
	String lookup(String n){
		Sym ls;
		for(int i = 0; i < symbolTable.size() ; i++){
			ls = symbolTable.get(i);
			if(ls.scope == Scope() && ls.name.equals(n) ){
				return ls.type;
			
			}
		
		
		
		}
		return null;
	}
	int Scope(){
		 return st.peek();
	}
	
	String type(String tr ,String tl , String ope){
		
		return null;
	}
	
	boolean importcheaker(String n){
		boolean b = false;
		try {
			Scanner s = new Scanner(new File(n+".etcl"));
			b = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			b = false;
		}
		return b;
	}
	void err(String s){
		Error += s;
	}
	
	
}
