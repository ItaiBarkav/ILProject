package Objects;
import java.util.Arrays;

public class block {
	private int Sn;
	private String Id;
	private int size;
	private int Delete;
	private int NumOfFiles;
	private int[] filesID;
	
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

	public void setDelete(int delete) {
		Delete = delete;
	}

	public int getSn() {
		return Sn;
	}

	public String getId() {
		return Id;
	}

	public int getSize() {
		return size;
	}

	public int getDelete() {
		return Delete;
	}

	public int getNumOfFiles() {
		return NumOfFiles;
	}

	public int[] getFilesID() {
		return filesID;
	}
	
}
