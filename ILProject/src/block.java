import java.util.Arrays;

public class block {
	int Sn;
	String Id;
	int size;
	int Delete;
	int NumOfFiles;
	int[] filesID;
	
	public block(int sn, String id, int size, int numOfFiles, int[] filesID) {
		super();
		Sn = sn;
		Id = id;
		this.size = size;
		this.Delete = 0;
		NumOfFiles = numOfFiles;
		this.filesID = filesID;
	}

	@Override
	public String toString() {
		return "block [Sn=" + Sn + ", Id=" + Id + ", size=" + size + ", Delete=" + Delete + ", NumOfFiles=" + NumOfFiles
				+ ", filesID=" + Arrays.toString(filesID) + "]";
	}
	
}
