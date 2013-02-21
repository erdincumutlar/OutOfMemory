package chabot.utils.outofmemory;

/*
 * DEV-9752
 * Signature

A heap dump from an out of memory error that was caused by this issue will include instances of the Account class whose retained heap size is over 400MB.

    Open the heap dump using Eclipse Memory Analyzer.
    Search for Account in the class histogram.
    Right click the Account class and select "Calculate Precise Retained Size".
    If the retained heap size is over 400MB, the OOM was caused by DEV-9752.
    If the retained heap size is under 400MB open the Dominator Tree
    Check if org.apache.catalina.loader.WebappClassLoader is over 300MB
    Expand org.apache.catalina.loader
    Expand com.patientkeeper.syncengine.SyncEngineCache if it is the majority of the WebappClassLoader memory right-click on com.patientkeeper.syncengine.StandardMap and select Java Basics, open in Dominator Tree.
    In the pop-up window select Group By Class from the group drop-down
    Expand all classes and check Account and PKPersonnel, if either have thousands of objects this is the cause of the out of memory.

    Please note that DEV-9752 can mask instances of DEV-19859. All cases exhibiting DEV-9752 should also be checked for evidence of DEV-19859.
    
    Resolved in 4.3.3
 */

public class Defect_09 {

}
