package Objects;
import java.util.Arrays;

public class block {
	private int Sn;				//	The serial number of the block.
	private String Id;			//	The id of the block.	
	private int size;			//	The size of the block.
	private int Delete;			//	This field indicates whether the block is deleted (1) or not (0).
	private int NumOfFiles;		//	The number of files that contain this block.
	private int[] filesID;		//	Array of id's of files that contain this block.
	
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
