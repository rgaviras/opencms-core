/*
 * File   : $Source: /alkacon/cvs/opencms/src/com/opencms/file/Attic/I_CmsRbFile.java,v $
 * Date   : $Date: 2000/02/25 16:55:09 $
 * Version: $Revision: 1.12 $
 *
 * Copyright (C) 2000  The OpenCms Group 
 * 
 * This File is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * For further information about OpenCms, please see the
 * OpenCms Website: http://www.opencms.com
 * 
 * You should have received a copy of the GNU General Public License
 * long with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.opencms.file;

import java.util.*;

import com.opencms.core.*;

/**
 * This interface describes a resource broker for files and folders in 
 * the Cms.<BR/>
 * <B>All</B> Methods get a first parameter: A_CmsUser. It is the current user. This 
 * is for security-reasons, to check if this current user has the rights to call the
 * method.<BR/>
 * 
 * All methods have package-visibility for security-reasons.
 * 
 * @author Andreas Schouten
 * @author Michael Emmerich
 * @version $Revision: 1.12 $ $Date: 2000/02/25 16:55:09 $
 */
 interface I_CmsRbFile {
	
	/**
	 * Creates a new file with the given content and resourcetype. <br>
	 * 
	 * Files can only be created in an offline project, the state of the new file
	 * is set to NEW (2). <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the folder-resource is not locked by another user</li>
	 * <li>the file dosn't exists</li>
	 * </ul>
	 * 
	 * @param user The user who own this file.
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param filename The name of the new file (including pathinformation).
	 * @param flags The flags of this resource.
	 * @param contents The contents of the new file.
	 * @param type The resourcetype of the new file.
	 * @return file The created file.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	 public CmsFile createFile(A_CmsUser user,
                               A_CmsProject project,
                               A_CmsProject onlineProject,
                               String filename, int flags,
							   byte[] contents, A_CmsResourceType type)
         		throws CmsException;
	
	/**
	 * Reads a file from the Cms.<BR>
	 * 
	 * A file can be read form an offline project and the online project, the state 
	 * of the file is unchanged.<BR>
	 * If the file is read from the online project, file header and file content are
	 * read  from the online project.<BR>
	 * If the file is read from an offline project and its state is CHANGED or NEW 
	 * (i.e. the file content is already present in the offline project), file and
	 * file content are read from the offline project.
	 * If the file is read from an offline project and its state is UNCHANGED, its 
	 * file content is not available in the offline project yet. Therefore, the file
	 * header is read from the offline project and the file content is read form the
	 * online project! <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read the resource</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param filename The name of the file to be read.
	 * 
	 * @return The file read from the Cms.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 * */
	 public CmsFile readFile(A_CmsProject project,
                             A_CmsProject onlineProject,
                             String filename)
		throws CmsException;
	
	/**
	 * Reads a file header from the Cms.<BR/>
	 * The reading excludes the filecontent. <br>
	 * 
	 * A file header can be read from an offline project or the online project.
	 *  
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read the resource</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param filename The name of the file to be read.
	 * 
	 * @return The file read from the Cms.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	 public A_CmsResource readFileHeader(A_CmsProject project, String filename)
		throws CmsException;
     
     
     /**
	 * Reads all file headers of a file in the OpenCms.<BR>
	 * This method returns a vector with the histroy of all file headers, i.e. 
	 * the file headers of a file, independent of the project they were attached to.<br>
	 * 
	 * The reading excludes the filecontent.
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user can read the resource</li>
	 * </ul>
	 * 
	 * @param filename The name of the file to be read.
	 * 
	 * @return Vector of file headers read from the Cms.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	 public Vector readAllFileHeaders(String filename)
		throws CmsException;
	
	/**
	 * Writes a file to the Cms.<br>
	 * 
	 * A file can only be written to an offline project.<br>
	 * The state of the resource is set to  CHANGED (1). The file content of the file
	 * is either updated (if it is already existing in the offline project), or created
	 * in the offline project (if it is not available there).<br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param file The file to write.
	 * @param changed Flag indicating if the file state must be set to changed.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void writeFile(A_CmsProject project,
                          A_CmsProject onlineProject,
                          CmsFile file,boolean changed)
		throws CmsException;
	
	/**
	 * Writes the fileheader to the Cms.<br>
	 * 
	 * A file header can only be written to an offline project.<br>
	 * The state of the resource is set to  CHANGED (1). If the file content is not
	 * exisiting in the offline project, it is read from the online project and written
	 * to the offline project as well. This is nescessary because all files in an 
	 * offline project with the state CHANGED (1) must have an existing file content in 
	 * the offline project as well. <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is  locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param file The file to write the header of.
     * @param changed Flag indicating if the file state must be set to changed.
 	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void writeFileHeader(A_CmsProject project, 
                                A_CmsProject onlineProject,
                                CmsFile file,boolean changed)
		throws CmsException;

	/**
	 * Renames the file to a new name. <br>
	 * 
	 * Rename can only be done in an offline project. To rename a file, the following
	 * steps have to be done:
	 * <ul>
	 * <li> Copy the file with the oldname to a file with the new name, the state 
	 * of the new file is set to NEW (2). 
	 * <ul>
	 * <li> If the state of the original file is UNCHANGED (0), the file content of the 
	 * file is read from the online project. </li>
	 * <li> If the state of the original file is CHANGED (1) or NEW (2) the file content
	 * of the file is read from the offline project. </li>
	 * </ul>
	 * </li>
	 * <li> Set the state of the old file to DELETED (3). </li> 
	 * </ul>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param oldname The complete path to the resource which will be renamed.
	 * @param newname The new name of the resource (A_CmsUser callingUser, No path information allowed).
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */		
	public void renameFile(A_CmsProject project, 
                           A_CmsProject onlineProject,
					       String oldname, String newname)
		throws CmsException;
	
	/**
	 * Deletes a file in the Cms.<br>
	 *
     * A file can only be deleteed in an offline project. 
     * A file is deleted by setting its state to DELETED (3). <br> 
     * 
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callinUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param filename The complete path of the file.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void deleteFile(A_CmsProject project, String filename)
		throws CmsException;
	
     /**
	 * Undeletes a file in the Cms.<br>
	 *
     * A file can only be deleted in an offline project.
     * Only files with the state DELETED can be undeleted. 
     * A file is deleted by setting its state to CHANGED (3). <br> 
     * 
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callinUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param filename The complete path of the file.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void undeleteFile(A_CmsProject project, String filename)
		throws CmsException;
    
    /**
	 * Removes a file in the Cms.<br>
	 *
     * In difference to the deleteFile method, the given file is physically removed <br> 
     * 
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param filename The complete path of the file.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void removeFile(A_CmsProject project, String filename)
		throws CmsException;
    
	/**
	 * Copies a file in the Cms. <br>
	 * 
     * A file can only be copied in an offline project. To copy a file, the following
	 * steps have to be done:
	 * <ul>
	 * <li> Copy the file with the sourcename to a file with the destinationname, the state 
	 * of the new file is set to NEW (2). 
	 * <ul>
	 * <li> If the state of the original file is UNCHANGED (0), the file content of the 
	 * file is read from the online project. </li>
	 * <li> If the state of the original file is CHANGED (1) or NEW (2) the file content
	 * of the file is read from the offline project. </li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read and write the sourceresource</li>
	 * <li>the user can write the destinationresource</li>
	 * <li>the sourceresource is locked by the user</li>
	 * <li>the destinationresource dosn't exists</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param source The complete path of the sourcefile.
	 * @param destination The complete path of the destinationfile.
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void copyFile(A_CmsProject project,
                         A_CmsProject onlineProject,
                         String source,String destination)
		throws CmsException ;
	

	/**
	 * Creates a new folder in the Cms.<br>
	 * 
	 * A new folder can only be created in an offline project. The state of the new
	 * folder is set to NEW (2). <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is not locked by another user</li>
	 * </ul>
	 * 
	 * @param user The user who owns the new folder.
	 * @param project The project in which the resource will be used.
	 * @param folder The name of the new folder (including pathinformation).
     * @param flags The flags of this resource.
	 * @return The created folder.
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public CmsFolder createFolder(A_CmsUser user,
                                  A_CmsProject project, 
                                  String folder,
                                  int flags)						
		throws CmsException;

	/**
	 * Reads a folder from the Cms.<br>
	 * 
	 * A folder can be either read from an offline Project or the online project.
	 * By reading a folder, its state is not changed. <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read the resource</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param folder The name of the folder to be read.
	 * 
	 * @return The read folder read from the Cms.
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	
    public CmsFolder readFolder(A_CmsProject project, String folder)
		throws CmsException;
	
	/**
	 * Writes a folder to the Cms.<br>
	 * 
	 * A folder can only be written to an offline project, the folder state is set to
	 * CHANGED (1).
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param folder The folder to write.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void writeFolder(A_CmsProject project, CmsFolder folder)
		throws CmsException;
    
	
	/**
	 * Deletes a folder in the Cms.<br>
	 * 
	 * Only folders in an offline Project can be deleted. A folder is deleted by 
	 * setting its state to DELETED (3). <br>
	 *  
	 * In its current implmentation, this method can ONLY delete empty folders.
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read and write this resource and all subresources</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param foldername The complete path of the folder.
	 * @param force If force is set to true, all sub-resources will be deleted.
	 * If force is set to false, the folder will be deleted only if it is empty.
	 * This parameter is not used yet as only empty folders can be deleted!
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void deleteFolder(A_CmsProject project, String foldername, boolean force)
		throws CmsException;
		
     /**
	 * Deletes a folder in the Cms.<br>
	 * 
     * In difference to the deleteFile method, the given file is physically removed <br> 
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read and write this resource and all subresources</li>
	 * <li>the resource is not locked</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param foldername The complete path of the folder.
	 * @param force If force is set to true, all sub-resources will be deleted.
	 * If force is set to false, the folder will be deleted only if it is empty.
	 * This parameter is not used yet as only empty folders can be deleted!
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */	
	public void removeFolder(A_CmsProject project, String foldername)
		throws CmsException ;

    
    
	/**
	 * Returns a Vector with all subfolders.<br>
	 * 
	 * Subfolders can be read from an offline project and the online project. <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read this resource</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param foldername the complete path to the folder.
	 * 
	 * @return subfolders A Vector with all subfolders for the given folder.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public Vector getSubFolders(A_CmsProject project, String foldername)
		throws CmsException;
	
	/**
	 * Returns a Vector with all files of a folder.<br>
	 * 
	 * Files of a folder can be read from an offline Project and the online Project.<br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can read this resource</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param foldername the complete path to the folder.
	 * 
	 * @return subfiles A Vector with all subfiles for the overgiven folder.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public Vector getFilesInFolder(A_CmsProject project, String foldername)
		throws CmsException;
	

	/**
	 * Changes the flags for this resource.<br>
	 * 
	 * Only the flags of a resource in an offline project can be changed. The state
	 * of the resource is set to CHANGED (1).
	 * If the content of this resource is not exisiting in the offline project already,
	 * it is read from the online project and written into the offline project.
	 * The user may change the flags, if he is admin of the resource <br>.
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param filename The complete path to the resource.
	 * @param flags The new accessflags for the resource.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public void chmod(A_CmsProject project,
                      A_CmsProject OnlineProject,
                      String filename, int flags)
		throws CmsException;
	
	/**
	 * Changes the owner for this resource.<br>
	 * 
	 * Only the owner of a resource in an offline project can be changed. The state
	 * of the resource is set to CHANGED (1).
	 * If the content of this resource is not exisiting in the offline project already,
	 * it is read from the online project and written into the offline project.
	 * The user may change this, if he is admin of the resource. <br>
	 * 
	 * <B>Security:</B>
	 * Access is cranted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user is owner of the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param filename The complete path to the resource.
	 * @param newOwner The new owner for this resource.
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public void chown(A_CmsProject project, 
                      A_CmsProject onlineProject,
                      String filename, A_CmsUser newOwner)
		throws CmsException;

	/**
	 * Changes the group for this resource<br>
	 * 
	 * Only the group of a resource in an offline project can be changed. The state
	 * of the resource is set to CHANGED (1).
	 * If the content of this resource is not exisiting in the offline project already,
	 * it is read from the online project and written into the offline project.
	 * The user may change this, if he is admin of the resource. <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user is owner of the resource</li>
	 * <li>the resource is locked by the callingUser</li>
	 * </ul>
	 * 
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param filename The complete path to the resource.
	 * @param newGroup The new group for this resource.
	 * 
     * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public void chgrp(A_CmsProject project,
                      A_CmsProject onlineProject,
                      String filename, A_CmsGroup newGroup)
		throws CmsException;

	/**
	 * Locks a resource.<br>
	 * 
	 * Only a resource in an offline project can be locked. The state of the resource
	 * is set to CHANGED (1).
	 * If the content of this resource is not exisiting in the offline project already,
	 * it is read from the online project and written into the offline project.
	 * A user can lock a resource, so he is the only one who can write this 
	 * resource. <br>
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is not locked by another user</li>
	 * </ul>
	 * 
	 * @param user The user who wants to lock the file.
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param resource The complete path to the resource to lock.
	 * @param force If force is true, a existing locking will be oberwritten.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 * It will also be thrown, if there is a existing lock
	 * and force was set to false.
	 */
	public void lockResource(A_CmsUser user,
                             A_CmsProject project, 
                             A_CmsProject onlineProject,String resource,
                             boolean force)
		throws CmsException;

    
	/**
	 * Unlocks a resource.<br>
	 * 
	 * Only a resource in an offline project can be unlock. The state of the resource
	 * is set to CHANGED (1).
	 * If the content of this resource is not exisiting in the offline project already,
	 * it is read from the online project and written into the offline project.
	 * Only the user who locked a resource can unlock it.
	 * 
	 * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is not locked by another user</li>
	 * </ul>
	 * 
	 * @param user The user who wants to lock the file.
	 * @param project The project in which the resource will be used.
	 * @param onlineProject The online project of the OpenCms.
	 * @param resource The complete path to the resource to lock.
	 * 
	 * @exception CmsException  Throws CmsException if operation was not succesful.
	 */
	public void unlockResource(A_CmsUser user,
                               A_CmsProject project,
                               A_CmsProject onlineProject,
                               String resource)
		throws CmsException;

    
    /**
     * Copies a resource from the online project to a new, specified project.<br>
     * Copying a resource will copy the file header or folder into the specified 
     * offline project and set its state to UNCHANGED.
     * 
     * <B>Security:</B>
	 * Access is granted, if:
	 * <ul>
	 * <li>the user has access to the project</li>
	 * <li>the user can write the resource</li>
	 * <li>the resource is not locked by another user</li>
	 * </ul>
     *	 
     * @param project The project to be published.
	 * @param onlineProject The online project of the OpenCms.
	 * @param resourcename The name of the resource.
	 * 
 	 * @exception CmsException  Throws CmsException if operation was not succesful.
     */
    public void copyResourceToProject(A_CmsProject project,
                                      A_CmsProject onlineProject,
                                      String resourcename)
        throws CmsException;
    
    /**
     * Publishes a specified project to the online project. <br>
     * This is done by copying all resources of the specified project to the online
     * project. The action proformed on the resources depends on the actual resource
     * state of each resource:
     * 
     * <ul>
     * <li> State UNCHANGED (0): Nothing is done with this resource. </li>
     * <li> State CHANGED (1): Copy resource to online project </li>
     * <li> State NEW (2): Copy resource to online project </li>
     * <li> State DELETED (3): Delete the resource in the online project </li>
     * </ul>
     *
     * @param project The project to be published.
	 * @param onlineProject The online project of the OpenCms.
	 * @return Vector of all resource names that are published.
     * @exception CmsException  Throws CmsException if operation was not succesful.
     */
    public Vector publishProject(A_CmsProject project, A_CmsProject onlineProject)
        throws CmsException;
	
}
