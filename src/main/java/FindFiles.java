import java.io.File;
import java.util.*;

public class FindFiles {

    private String startPath = "Z:/Downloads";
    List<AFile> foundFiles = new ArrayList<>();

    public FindFiles() {

        File sf = new File(startPath);
        this.FileList(sf);

    }

    public void FileFinder(File sf) {

        File list[] = sf.listFiles();
        for (File file : list) {
            if (file.isDirectory() && file.canRead()) {
                this.FileFinder(file);
            } else {
                AFile foundIt = new AFile();
                foundIt.setAbspath(file.getAbsolutePath());
                foundIt.setLastmodified(file.lastModified());
                foundIt.setName(file.getName());
                foundIt.setLength(file.length());
                String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                foundIt.setExt(extension);

                foundFiles.add(foundIt);
            }
        }
    }

    public void FileList(File sf) {
        FileFinder(sf);
        //System.out.println("Size of foundFiles = " + foundFiles.size());
        for (AFile a : foundFiles) {
            //System.out.println(a.getAbspath() + " | " + a.getLength());
        }
        this.FileSort();

    }

    public void FileSort() {

        HashMap<Integer, AFileList> hmap = new HashMap<Integer, AFileList>();

        for (AFile a : foundFiles) {
            Integer index = a.getLength().intValue();
            AFileList alist = hmap.get(index);
            if (alist == null) {
                AFileList afilelist = new AFileList();
                List<AFile> afl = new ArrayList<AFile>();
                afl.add(a);
                afilelist.setAfile(afl);
                hmap.put(index, afilelist);
            } else {
                AFileList afilelist = hmap.get(index);
                List<AFile> afl = afilelist.getAfile();
                afl.add(a);
                afilelist.setAfile(afl);
            }
        }

        List<AFile> duplicates = new ArrayList<AFile>();

        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            //System.out.println("Key = " + entry.getKey());
            AFileList afilelist = (AFileList)entry.getValue();
            if (afilelist.getAfile().size() > 1) {

                List<AFile> filelist = afilelist.getAfile();
                for (AFile af : filelist) {
                    //System.out.printf("%s | %s | %s\n", af.getAbspath(), af.getLength(), af.getName());
                    duplicates.add(af);
                }


            } else {
                //
            }
        }

        System.out.println("Total files scanned: " + foundFiles.size());
        System.out.println("Found duplicates   : " + duplicates.size());

        for (AFile af : duplicates) {
            // TODO: MD5 + SHAxxx
        }

    }

}
