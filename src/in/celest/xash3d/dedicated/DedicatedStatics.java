package in.celest.xash3d.dedicated;
import android.widget.*;
import java.util.*;

public class DedicatedStatics
{
	public static DedicatedActivity launched = null;
	public static List<String> 	logView = new ArrayList();
	
	public static String MESS_SERVICE_STARTING	= "[XashDSAndroid]\033[33mStarting service...\033[0m";
	public static String MESS_BINARIES_STARTING = "[XashDSAndroid]\033[33mExecuting binaries...\033[0m";
	public static String MESS_SERVICE_STARTED 	= "[XashDSAndroid]\033[32mService successfully started!\033[0m";
	public static String MESS_SERVICE_KILLING 	= "[XashDSAndroid]\033[33mKilling service...\033[0m";
	public static String MESS_SERVICE_KILLED 	= "[XashDSAndroid]\033[31mService killed.\033[0m";
	
	public static String XASH_BINARY 	= "xash-old";
	public static String XASH_BINARY_SSE= "xash_sse2";
	
	private static String XASH_BINARY_NEW 	= "xash";
	private static String XASH_BINARY_SSE_NEW= "xash";
	private static String XASH_BINARY_OLD 	= "xash-old";
	private static String XASH_BINARY_SSE_OLD= "xash_sse2";
	
	public static void chstr(boolean isnew)
	{
		if (isnew)
		{
			XASH_BINARY = XASH_BINARY_NEW;
			XASH_BINARY_SSE = XASH_BINARY_SSE_NEW;
		} else {
			XASH_BINARY = XASH_BINARY_OLD;
			XASH_BINARY_SSE = XASH_BINARY_SSE_OLD;
		}
	}
}
