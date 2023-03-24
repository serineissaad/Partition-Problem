package meta_dm2;
import java.util.*;


public class mindiff_a_star {
   
	public static class node { 
	    private int min;
	    private List<Integer> t1;
	    private List<Integer> t2;

	    public node(int l, List<Integer> t1, List<Integer> t2) {
	        this.min = l;
	        this.t1 = t1;
	        this.t2 = t2;
	    }

	    public int getMin() {
	        return min;
	    }

	    public void setMin(int l) {
	        this.min = l;
	    }

	    public List<Integer> getT1() {
	        return t1;
	    }

	    public void setT1(List<Integer> t1) {
	        this.t1 = t1;
	    }

	    public List<Integer> getT2() {
	        return t2;
	    }

	    public void setT2(List<Integer> t2) {
	        this.t2 = t2;
	    }
	}

	public static int calculateD(int n) {
		int d= (int)Math.floor(n/2) + 1;
		return d;
	}
	
	    public static List<node> combinedfs(List<Integer> arr) {
	        List<node> fermee = new ArrayList<>();
	        List<node> fermeemin = new ArrayList<>();//liste des solutions optimales (difference=difference minimale)
	        Stack<Integer> stack = new Stack<>();
	        int min=arr.stream().reduce(0, Integer::sum);
	        int not_reached=1;
	        
	        node myMin = new node(min, new ArrayList<Integer>(), new ArrayList<Integer>());
	        
	        int n = arr.size();
	        int d= calculateD(n);

	            int i=1;
	        	stack.push(i);
	            not_reached=subtreedfs(arr, stack, i, n, fermee,myMin,not_reached);
	            stack.pop();

	            if(not_reached==1)
	            	System.out.println("no solution");
	        
	        	int size=fermee.size();
      
	        return fermee;
	    }
	    
	    public static List<node> combine(List<Integer> arr) {
	        List<node> fermee = new ArrayList<>();
	        List<node> fermeemin = new ArrayList<>();//liste des solutions optimales (difference=difference minimale)
	        Stack<Integer> stack = new Stack<>();
	        int min=arr.stream().reduce(0, Integer::sum);
	        Collections.sort(arr, Collections.reverseOrder());
	        int not_reached=1;
	        
	        node myMin = new node(min, new ArrayList<Integer>(), new ArrayList<Integer>());
	        
	        int n = arr.size();
	        int d= calculateD(n);
	            int i=1;
	        	stack.push(i);
	            not_reached=subtree(arr, stack, i, n, fermee,myMin,not_reached);
	            stack.pop();
	          	int size=fermee.size();
	        return fermee;
	    }
	    
	    
	    public static int subtree(List<Integer> arr, Stack<Integer> stack, int index, int n, List<node> fermee,node min,int not_reached) {
	        List<Integer> group = new ArrayList<>();
	        int S1=0,S2; int current;
	        for (int i = 0; i < stack.size(); i++) {
	            current=arr.get(stack.elementAt(i) - 1);
	            group.add(current);
	            S1=S1+current;
	        }
	        int s=arr.stream().reduce(0, Integer::sum);
	      	        
	        node node=create_node(arr,arr.size(),group,group.size(),S1);	        
	      	 
	        int diff=node.getMin();
	   	    fermee.add(node);
	        if(s%2==1)
	        	return 1;
	        if(S1<=s/2) {
	        	
	        	if(S1==s/2) {
	        		return 0;
	        		}
	        	for (int i = index + 1 ; i <= n && (not_reached!=2 || not_reached!=0); i++) {
		            stack.push(i);
		            not_reached=subtree(arr, stack, i, n, fermee,min,not_reached);
		            if(not_reached==0)
		            	return 0;
		            stack.pop();
		        }
	        	if(not_reached==2) {
	        	not_reached=1;}
	        	
	        }else {
	        		return 2;
	        }
	  
	        return not_reached;
	    }
	    
	    public static int subtreedfs(List<Integer> arr, Stack<Integer> stack, int index, int n, List<node> fermee,node min,int not_reached) {
	        List<Integer> group = new ArrayList<>();
	        int S1=0,S2; int current;
	        for (int i = 0; i < stack.size(); i++) {
	            current=arr.get(stack.elementAt(i) - 1);
	            group.add(current);
	            S1=S1+current;
	        }
	        int s=arr.stream().reduce(0, Integer::sum);
	      	        
	        node node=create_node(arr,arr.size(),group,group.size(),S1);	        
	      	 
	   	    fermee.add(node);
	   	    
	   	 int diff=node.getMin();
	        if(node.getMin()==0) {
	        	return 0;
	        }       	        
       
	        for (int i = index + 1 ; i <= n && not_reached!=0; i++) {
	            stack.push(i);
	            not_reached=subtree(arr, stack, i, n, fermee,min,not_reached);
	            if(not_reached==0)
	            	return 0;
	            stack.pop();
	        }
	   	    
	        return not_reached;
	    }
	    
	    public static List<Integer> generateRandomArray(int n) 
	   {
		   List<Integer> S = new ArrayList<>();
	       Random rand = new Random();
	       for (int i = 0; i < n; i++) {S.add(rand.nextInt(50) + 1);}
	       return S;
	   }
	    public static node create_node(List<Integer> arr,int arr_size,List<Integer> tab1, int tab1_size,int S1){
	        int S2=0;
	       
	    List<Integer> tab2 = new ArrayList<>();
	    List<Integer> copy = new ArrayList<>();
	    for(int i=0;i<tab1_size;i++){
	    	copy.add(tab1.get(i));
	    }

	    for(int i=0;i<arr_size;i++){
	         int current=arr.get(i);
		     if(!copy.contains(current)){
	                tab2.add(current);
	                S2=S2+current;}
	         else {
	        	 copy.remove(Integer.valueOf(current));
	         }
	        }
	    node myMin = new node(Math.abs(S1-S2), tab1, tab2);
	       return myMin;
	   }

	    public static void main(String[] args) {
	    	List<Integer> s0= Arrays.asList(1,2,3,4);//6,27,30,30,31,35,48,40
          List<Integer> s1  = Arrays.asList(456 ,78  ,24 ,32 ,98 ,204 ,68);//2,34
          List<Integer> s2=Arrays.asList(2, 3, 5, 6, 7, 8, 15, 16);//6,6
          List<Integer> s3=Arrays.asList(1, 2, 3, 4, 5, 7, 28 ,  10 ,0);//68,138
          List<Integer> s4= Arrays.asList(6, 13, 2, 15, 21, 8, 17, 4, 6, 26);//12,58
          List<Integer> s5= Arrays.asList(12, 10, 8, 7, 5, 4, 3, 2, 2, 1, 10);//4,244
          List<Integer> s6= Arrays.asList(10, 6, 8, 2, 5, 1, 9, 7, 4, 3, 11, 12);//7,39
          List<Integer> s7= Arrays.asList(3, 6, 8, 10, 12, 13, 14, 15, 16, 18, 20, 21, 26,3, 6, 8, 10, 12, 13, 14, 15, 16, 18, 20, 21, 26);//13,19
          List<Integer> s8 = Arrays.asList(6, 8, 4, 1, 2, 7, 10, 12, 9, 5, 3, 15, 16, 14, 11, 13, 18, 16);//17,136

	    	List<Integer> ss=generateRandomArray(8);
	    	//System.out.println(s2);
	    	for(List<Integer> arr:list) {
	    	int i;double s=0,sa=0;double duration=0,durationa=0;
	        List<node> fermee = new ArrayList<>();
	        List<node> fermeea = new ArrayList<>();

	        for(i=0;i<30;i++) {
	        long startTime =  System.currentTimeMillis();
	        fermee=combinedfs(arr);
	        long endTime =   System.currentTimeMillis();
	        duration = (endTime - startTime);
	        s=s+duration;
	     	        }
	        
	        for(i=0;i<30;i++) {
		        long startTimea =  System.currentTimeMillis();
		        fermeea=combine(arr);
		        long endTimea =System.currentTimeMillis();
		        durationa = (endTimea - startTimea);
		        sa=sa+durationa;
		        }
	         
	        //System.out.println(arr.size());
	        System.out.println(arr.size()+","+s/30+","+sa/30+","+fermee.size()+","+fermeea.size());
	        //System.out.println("nodes: dfs: "+fermee.size()+", astar: "+fermeea.size()+"\n\n");}
	        /**/
	        /*for(i=0;i<fermee.size();i++) {
    		System.out.println(fermee.get(i).t1+"  "+fermee.get(i).t2+" diff="+fermee.get(i).min+"\n");
	        }
	        for(i=0;i<fermeea.size();i++) {
	    		System.out.println(fermeea.get(i).t1+"  "+fermeea.get(i).t2+" diff="+fermeea.get(i).min+"\n");
		    }*/
	        
	        }
	    
	}}