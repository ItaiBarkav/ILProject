import java.util.Arrays;

public class file {
	int Sn;
	String Id;
	int size;
	int Delete;
	int NumOfBlocks;
	int[] BlocksID;
	
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
	
}
