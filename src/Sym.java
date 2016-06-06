
public class Sym {
	private String n = "------";
	String name = n ;
	String type = n;
	int scope = -9999;
	String parent = n;
	String acc = n;
	String stat = n;

	public Sym(String name,String type,int scope){
		this.name = name;
		this.type = type;
		this.scope = scope;
	
	}
	
	public String symType(){
		if(type.equals("class")) return "class";
		else if(!acc.equals(n)) return "global";
		else return "local";
	}
	public String show(){
		return name+" "+type+" "+acc+" "+stat+" "+parent;
	}
}
