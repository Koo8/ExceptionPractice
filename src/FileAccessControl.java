import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.Set;

public class FileAccessControl {

    public static void main(String[] args) throws IOException {
        makeFileNonReadable("C:\\Users\\NancyPC\\Desktop\\Todelete");
    }

    //"you have been dennied to access this folder"
    private static void makeFileNonReadable(String file) throws IOException {
        Path filePath = Paths.get(file);
        Set<String> supportedAttr = filePath.getFileSystem().supportedFileAttributeViews();
        System.out.println(supportedAttr); // [owner, dos, acl, basic, user]
        // how to set permission for PosixFileAttributeView
        if (supportedAttr.contains("posix")) {
            System.out.println("this file is posix");
            Files.setPosixFilePermissions(filePath, PosixFilePermissions.fromString("-w--w----")); // rwx set for read write and execute, three groups for owner, group and memeber
            // how to set permission for aclFileAttributeView
        } else if (supportedAttr.contains("acl")) {
            System.out.println("this file is acl");
            UserPrincipal fileOwner = Files.getOwner(filePath);  // for OwnerFileAttributeView, AclFileAttributeView is the subclass
            // get the AclFileAttributeView to Read the access control list
            AclFileAttributeView view = Files.getFileAttributeView(filePath, AclFileAttributeView.class);
            AclEntry entry = AclEntry.newBuilder()
                    .setType(AclEntryType.DENY)
                    .setPrincipal(fileOwner)
                    .setPermissions(AclEntryPermission.READ_DATA)
                    .build();
            // read the acl - The returned list of acl is modifiable so as to facilitate changes to the
            //existing ACL. The {@link #setAcl setAcl} method is used to update
            //the file's ACL attribute.
            List<AclEntry> acl = view.getAcl(); // read the acl -
            System.out.println(acl.size()+ " is the acl size b4 add new component");
            System.out.println(acl);
            // acl used to have 4 members in the list, this new permission is added at index 0
            acl.add(0, entry); // the DENY permission has to be added to index 0, othewise not working.
            view.setAcl(acl);
        }
    }
}
