package Objects;
import java.util.Arrays;

public class file {
	private int Sn;				//	The serial number of the file.
	private String Id;			//	The id of the file.
	private int size;			//	The size of the file.
	private int Delete;			//	This field indicates whether the file is deleted (1) or not (0).
	private int NumOfBlocks;	//	The number of blocks that make up the file.
	private int[] BlocksID;		//	Array of id's of blocks that make up the file.
	
	public file(int sn, String id, int size, int numOfBlocks, int[] blocksID) {
		super();
		Sn = sn;
		Id = id;
		this.size = size;
		this.Delete = 0;
		NumOfBlocks = numOfBlocks;
		BlocksID = blocksID;
	}

	@Override
	public String toString() {
		return "file [Sn=" + Sn + ", Id=" + Id + ", size=" + size + ", Delete=" + Delete + ", NumOfBlocks="
				+ NumOfBlocks + ", BlocksID=" + Arrays.toString(BlocksID) + "]";
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

	public int getNumOfBlocks() {
		return NumOfBlocks;
	}

	public int[] getBlocksID() {
		return BlocksID;
	}
	
}
