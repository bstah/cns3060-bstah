#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <dirent.h>

int list_dir (const char * dir_name)
{
    int pMax = 300;
    DIR * d;
    d = opendir (dir_name);
    if (! d) {
        perror("unable to open directory");
        return -1;
    }
    while (1) {
        struct dirent * entry;
        const char * dname;
        entry = readdir (d);
        if (! entry) {
            break;
        }
        dname = entry->d_name;
        struct stat estat;

        if(stat(dname,&estat)!=0)
        {
          perror("Failed to get file stats");
          continue;
        }
       
        if(strcmp(dname,"..")!=0)
        {
          printf ("%lld\t%s%s\n",(long long)estat.st_size,dir_name, dname);
        }
        if (entry->d_type & DT_DIR) { 
            if (strcmp (dname, "..") != 0 && strcmp (dname, ".") != 0)
            {
                int path_length;
                char path[pMax];
 
                path_length = snprintf (path, pMax,"%s/%s", dir_name, dname);
                if (path_length >= pMax)
                {
                    perror("path too long");
                    return -1;
                }
                
                list_dir (path);
            }
        }
    }
    if (closedir (d)) {
        perror("unable to close directory");
        return -1;
    }
    return 0;
}

int main (int argc,char* argv[])
{
    const char* curDir = argc > 1 ? argv[1] : ".";
    int status = list_dir (curDir);
    return status;
}
