package Objects;
import java.util.Arrays;

public class file {
	private int Sn;
	private String Id;
	private int size;
	private int Delete;
	private int NumOfBlocks;
	private int[] BlocksID;
	
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
