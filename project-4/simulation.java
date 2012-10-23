import java.util.*;

public class simulation {
	static final int begin = 15;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nextNum = in.nextInt();
		
		int[] temp = new int[200];
		int i = 0;
		while (nextNum >= 0){
			temp[i] = nextNum;
			nextNum = in.nextInt();
			i++;
		}
		int[] data = new int[i];
		for(i = 0; i < data.length; i++){
			data[i] = temp[i];
		}
		firstComeFirstServe(data);
		shortestSeekTime(data);
		elevator(data);
		in.close();
	}
	
	static public void firstComeFirstServe(int[] data){
		System.out.println("First Come First Serve");
		System.out.println("Head Movement\t\tTracks Traversed");
		int last = begin;
		int track;
		int totalTracks = 0;
		for (int i = 0; i < data.length;i++){
			if(last > data[i]){
				track = last - data[i];
			}
			else{
				track = data[i] - last;
			}
			System.out.println("Tracks " + last + " - " + data[i] + "\t\t" + track);
			totalTracks += track;
			last = data[i];
		}
		System.out.println("Total Tracks\t\t" + totalTracks + "\n");
	}
	
	static public void shortestSeekTime(int[] data){
		int[] temp = new int[data.length];
		int last = begin;
		int dataPoint = 0;
		int shortTrack = -1;
		int currentTrack;
		int totalTracks = 0;
		System.out.println("Shortest Seek Time");
		System.out.println("Head Movement\t\tTracks Traversed");
		for(int j = 0; j < data.length;j++){
			for(int i = 0; i < data.length;i++){
				boolean isMember = false;
				for(int k = 0;k<temp.length;k++){
					if(temp[k] == data[i]){
						isMember = true;
						break;
					}
				}
				if(isMember == false){
					if(last > data[i]){
						currentTrack = last - data[i];
					}
					else
						currentTrack = data[i] - last;
					if(shortTrack < 0){
						shortTrack = currentTrack;
						dataPoint = i;
					}
					else{
						if(currentTrack < shortTrack){
							shortTrack = currentTrack;
							dataPoint = i;
						}
					}
				}
			}
			System.out.println("Tracks " + last + " - " + data[dataPoint] + "\t\t" + shortTrack);
			totalTracks += shortTrack;
			last = data[dataPoint];
			temp[j] = data[dataPoint];
			shortTrack = -1;
		}
		System.out.println("Total Tracks\t\t" + totalTracks + "\n");
	}
	
	static public void elevator(int[] data){
		int[] temp = data;
		Arrays.sort(temp);
		int last = begin;
		int track;
		int totalTracks = 0;
		int startPoint = 0;
		System.out.println("Scan-Look(Elevator)");
		System.out.println("Head Movement\t\tTracks Traversed");
		int i = 0;
		for(i = 0; i < data.length; i++){
			if(data[i] >= begin)
				break;
			startPoint++;
		}
		if(startPoint < data.length){
			for( i = startPoint;i < data.length;i++){
				track = temp[i] - last;
				System.out.println("Tracks " + last + " - " + temp[i] + "\t\t" + track);
				totalTracks += track;
				last = temp[i];
			}
		}
		else
			startPoint--;
		for( i = startPoint-1; i >= 0; i--){
			track = last - temp[i];
			System.out.println("Tracks " + last + " - " + temp[i] + "\t\t" + track);
			totalTracks += track;
			last = temp[i];
		}
		System.out.println("Total Tracks\t\t" + totalTracks);
	}

}
