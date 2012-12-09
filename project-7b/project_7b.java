import java.text.DecimalFormat;
import java.util.*;

public class project_7b {
	public static void main(String[] args) {
		try{
		Scanner in = new Scanner(System.in);
		List<Data> data = new ArrayList<Data>();
		int i = 0;
		int input;
		while(in.hasNext()){
			data.add(new Data());
			input = in.nextInt();
			data.get(i).setID(input);
			input = in.nextInt();
			data.get(i).setArrivalTime(input);
			input = in.nextInt();
			data.get(i).setTimeToComplete(input);
			data.get(i).resetTime();
			i++;
		}
		in.close();
		firstComeFirstServe(data);
		shortestJobFirst(data);
		shortestRemainingTimeNext(data);
		roundRobin(data,0);
		roundRobin(data,0.4);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	static public void firstComeFirstServe(List<Data> d){
		System.out.println("Pid\tWait\tTurnaround\n---\t----\t----------");
		int wait;
		int turn;
		double aveWait = 0;
		double aveTurn = 0;
		int total = 0;
		for(int i = 0; i < d.size(); i++){
			total += d.get(i).getTimeToComplete();
			turn = total - d.get(i).getArrivalTime();
			wait = turn - d.get(i).getTimeToComplete();
			aveTurn += turn;
			aveWait += wait;
			System.out.println(d.get(i).getID() + "\t" + wait + "\t" + turn);
		}
		aveTurn = aveTurn/d.size();
		aveWait = aveWait/d.size();
    DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Average wait: " + aveWait + " Average turnaround: " + aveTurn);
	}
	static public void shortestJobFirst(List<Data> d){
		System.out.println("Pid\tWait\tTurnaround\n---\t----\t----------");
		double aveWait = 0;
		double aveTurn = 0;
		int total = 0;
		int smallest = d.get(0).getTimeToComplete();
		int current = 0;
		List<Data> temp = new ArrayList<Data>();
		for(int i = 0; i<d.size(); i++){
			for(int j = 0; j < d.size(); j++){
				if(d.get(j).getArrivalTime() <= total && !compare(temp,d.get(j))){
					if(smallest > d.get(j).getTimeToComplete()){
						smallest = d.get(j).getTimeToComplete();
						current = j;
					}
				}
			}
			total += d.get(current).getTimeToComplete();
			d.get(current).setTurnaround( total - d.get(current).getArrivalTime());
			d.get(current).setWait( d.get(current).getTurnaround() - d.get(current).getTimeToComplete());
			aveTurn += d.get(current).getTurnaround();
			aveWait += d.get(current).getWait();
			temp.add(d.get(current));
			smallest = 10000;
		}
		
		for(int i = 0; i < d.size(); i++){
			System.out.println(d.get(i).getID() + "\t" + (int)d.get(i).getWait() + "\t" + (int)d.get(i).getTurnaround());
		}
		aveTurn = aveTurn/d.size();
		aveWait = aveWait/d.size();
    DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Average wait: " + df.format(aveWait) + " Average turnaround: " + df.format(aveTurn));
	}
	static public boolean compare(List<Data> d1, Data d2){
		for(int i = 0; i<d1.size(); i++){
			if(d1.get(i).getID() == d2.getID())
				return true;
		}
		return false;
	}
	static public void shortestRemainingTimeNext(List<Data> d){
		System.out.println("Pid\tWait\tTurnaround\n---\t----\t----------");
		for(int i = 0; i<d.size();i++){
			d.get(i).resetTime();
		}
		int i = 0;
		double aveWait = 0;
		double aveTurn = 0;
		int total = 0;
		int current = 0;
		List<Data> temp = new ArrayList<Data>();
		int smallest = 1000;
		while(i<d.size()){
			for(int j = 0; j < d.size(); j++){
				if(d.get(j).getArrivalTime() <= total && !compare(temp,d.get(j))){
					if(smallest > d.get(j).getRemainingTime()){
						smallest = d.get(j).getRemainingTime();
						current = j;
					}
				}
			}
			if(d.get(current).getWait() < 0){
				d.get(current).setWait(total - d.get(current).getArrivalTime());
				aveWait += total - d.get(current).getArrivalTime();
			}
			total++;
			d.get(current).setRemainingTime(d.get(current).getRemainingTime()-1);
			if(d.get(current).getRemainingTime() == 0){
				d.get(current).setTurnaround(total - d.get(current).getArrivalTime());
				aveTurn += d.get(current).getTurnaround();
				temp.add(d.get(current));
				i++;
			}
			smallest = 10000;
		}
		for(i = 0; i < d.size(); i++){
			System.out.println(d.get(i).getID() + "\t" + (int)d.get(i).getWait() + "\t" + (int)d.get(i).getTurnaround());
		}
		aveTurn = aveTurn/d.size();
		aveWait = aveWait/d.size();
    DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Average wait: " + df.format(aveWait) + " Average turnaround: " + df.format(aveTurn));
	}
	
	static public void roundRobin(List<Data> d,double conSwitch){
		System.out.println("Pid\tWait\tTurnaround\n---\t----\t----------");
		for(int i = 0; i<d.size();i++){
			d.get(i).resetTime();
		}
		LinkedList<Data> temp = new LinkedList<Data>();
		List<Data> done = new ArrayList<Data>();
		Data tdata = null;
		double total = 0;
		double aveWait = 0;
		double aveTurn = 0;
		int i = 0;
		while(i < d.size()){
			for(int j = 0; j < d.size(); j++){
				if(d.get(j).getArrivalTime() <= total && !compare(temp,d.get(j)) && !compare(done,d.get(j))){
					temp.add(d.get(j));
				}
			}
			if(tdata != null && !compare(done,tdata)){
				temp.remove(tdata);
				temp.addLast(tdata);
			}
			else if(tdata != null){
				temp.remove(tdata);
			}
			tdata = null;
			if(temp.getFirst().getWait() < 0){
				temp.getFirst().setWait(total - temp.getFirst().getArrivalTime());
				aveWait += total - temp.getFirst().getArrivalTime();
			}
			if(temp.getFirst().getRemainingTime() <= 4){
				total += temp.getFirst().getRemainingTime();
				temp.getFirst().setRemainingTime(0);
				temp.getFirst().setTurnaround(total - temp.getFirst().getArrivalTime()); 
				aveTurn += total - temp.getFirst().getArrivalTime();
				done.add(temp.getFirst());
				tdata = temp.getFirst();
				//temp.removeFirst();
				i++;
			}
			else{
				temp.getFirst().setRemainingTime(temp.getFirst().getRemainingTime() - 4);
				total += 4;
				tdata = temp.getFirst();
				//temp.removeFirst();
			}
			total += conSwitch;
		}
		DecimalFormat f = new DecimalFormat("#.#");
		for(i = 0; i < d.size(); i++){
			System.out.println(d.get(i).getID() + "\t" + f.format(d.get(i).getWait()) + "\t" + f.format(d.get(i).getTurnaround()));
		}
		aveTurn = aveTurn/d.size();
		aveWait = aveWait/d.size();
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Average wait: " + df.format(aveWait) + " Average turnaround: " + df.format(aveTurn));
		
	}
	
}	
	class Data{
		private int ID;
		private int arrivalTime;
		private int timeToComplete;
		private double turnaround;
		private double wait;
		private int remainingTime;
		
		public void resetTime(){
			remainingTime = timeToComplete;
			wait = -1;
		}
		public void setID(int i){
			ID = i;
		}
		public void setArrivalTime(int i){
			arrivalTime = i;
		}
		public void setTimeToComplete(int i){
			timeToComplete = i;
		}
		public void setTurnaround(double i){
			turnaround = i;
		}
		public void setWait(double i){
			wait = i;
		}
		public void setRemainingTime(int i){
			remainingTime = i;
		}
		public int getID(){
			return ID;
		}
		public int getArrivalTime(){
			return arrivalTime;
		}
		public int getTimeToComplete(){
			return timeToComplete;
		}
		public double getTurnaround(){
			return turnaround;
		}
		public double getWait(){
			return wait;
		}
		public int getRemainingTime(){
			return remainingTime;
		}
	}


