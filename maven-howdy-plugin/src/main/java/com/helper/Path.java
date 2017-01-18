package com.helper;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class Path {
	private static Repository localRepo;
	private static Git result;
	private static String localpath,REMOTE_URL;
	private static File folder;

	public static void setUp(){
		REMOTE_URL="ssh://git@git.eng.vmware.com/vra-patches.git";
		String str=System.getProperty("user.dir");
		localpath=new File(str).getParentFile().getAbsolutePath();
		folder = new File(localpath+"/GitPatchRepository");
	}
	public static void create() throws  IOException, GitAPIException{
		if (!folder.exists()) {
			if (folder.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		localRepo=new FileRepository(folder.getAbsolutePath()+ "/.git");
		System.out.println(folder);
	}

	public static void init() throws  IOException, GitAPIException{
		Git git = Git.init().setDirectory(folder).call();
		System.out.println("Created a new repository at " + git.getRepository().getDirectory());
	}

	public static void main(String[] args) throws IOException, GitAPIException {
		setUp();
		//create();

		//		init();
		
		
		testClone();
		/*testAdd();
			testCommit();
			testPush();*/
		//detectChange();
	}


	public static void recursiveDelete(File file) {
		//to end the recursive loop
		if (!file.exists())
			return;

		//if directory, go inside and call recursively
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				//call recursively
				recursiveDelete(f);
			}
		}
		//call delete to delete files and empty directory
		file.delete();
		System.out.println("Deleted file/folder: "+file.getAbsolutePath());
	}

	public static void testClone() throws IOException, GitAPIException {

		//If the folder is already create then delete this folder then clone another copy of the folder 

		System.out.println(folder +"this is a folder properties ");
		if(folder.exists()){
			recursiveDelete(folder);
		}
		//
		create();
		result = Git.cloneRepository()
				.setURI(REMOTE_URL)
				.setDirectory(folder)
				.call();
		// Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
		System.out.println("Having repository: " + result.getRepository().getDirectory());

	}
	

	public static void testAdd() throws IOException, GitAPIException {
		System.out.println("This is add method "+ result);
		File myfile = new File(folder + "/dummyfile3");
		myfile.createNewFile();
		result.add().addFilepattern("dummyfile2").call();
	}

	public static void testCommit() throws IOException, GitAPIException,
	JGitInternalException {
		result.commit().setMessage("Innitial commit of the dummy file1 ").call();
	}

	public static void testPush() throws IOException, JGitInternalException,
	GitAPIException {
		result.push().call();
	}

	// Write the code to detect the changes in the repository 
	// Then Add those changes 
	// then commit the changes 
	// Then Push the changes to the Git remote repository
	
	
	//clone the remote repo then add new file in the specified folder in the repository   // detect the changes  // Add the new chnges to staging 
	
	 public static Repository openJGitRepository() throws IOException {
	        FileRepositoryBuilder builder = new FileRepositoryBuilder();
//	        builder.findGitDir( folder);
	        return builder
	                .readEnvironment() // scan environment GIT_* variables
	                .findGitDir(folder) // scan up the file system tree
	                .build();
	        
	    }
	 public static void addFile( Git git) throws IOException, GitAPIException {
		/*	//File myfile = new File(folder + "/"+file);
			//myfile.createNewFile();
			git.add().addFilepattern(file).call();
//			testCommit();
//			testPush();
			git.commit().setMessage("Commit changes patch 03").call();
			git.push().call();
			System.out.println();
			*/
			
			// New Code 
			
		  System.out.println("This is add method "+ git);
			File myfile = new File(folder.toString() + "\\patches\\dummyfile4040");
			myfile.createNewFile();
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Commit changes patches and new file added").call();
			git.push().call();
			System.out.println("Done just check");
		 
		}

	public static void detectChange() throws NoWorkTreeException, GitAPIException, IOException{
		
		try (Repository repository = openJGitRepository()) {
            System.out.println("Listing  directory :"+repository.getDirectory());
            try (Git git = new Git(repository)) {
            	/*System.out.println("Having repository: " + git.getRepository().getDirectory());
                Status status = git.status().call();
//                System.out.println("The status of the repository is "+ status.get);
                Set<String> untrackedFile = status.getUntracked();
                System.out.println("size of the untracked  set"+untrackedFile.size());
                for(String untrack : untrackedFile) {
                    System.out.println("Untracked: " + untrack);
                    String filename[]=untrack.split("/");
                    System.out.println("the filename is "+filename[filename.length-1]);
                    String s=folder.toString();
                    String test = untrack.replace('/','\\');
                    s=s+'\\'+test;
                    System.out.println("the modified path is "+ s);*/
                   // addFile(s, git);
             //   }
            System.out.println("fol val  "+folder);
    		addFile(git);
            }
		}
         }
	}
