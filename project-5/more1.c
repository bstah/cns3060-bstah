
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <string.h>
#include <dirent.h>
#include <signal.h>
#include <termios.h>
#include <fcntl.h>
#include <unistd.h>
#define MAX 300

tty_mode(int how);
void signalResponse(int value){
    tty_mode(1);
    printf("terminal settings restored terminating...");
    exit(0);
}

void printline(FILE* file){
  char line[MAX];
  if(fgets(line,MAX,file) != NULL){
    printf("%s",line);
  }
}

void printfile(FILE* file){
  int i = 0;
  char line[MAX];
  while(fgets(line,MAX,file) != NULL){
    if(i<23)
      printf("%s",line);
    else
      break;
    i++;
  }

}


void setNodelay(){
  struct termios ttystate;
  tcgetattr(0, &ttystate);
  ttystate.c_lflag &= ~ICANON;
  ttystate.c_lflag &= ~ECHO;
  ttystate.c_cc[VMIN] = 1;
  tcsetattr(0,TCSANOW,&ttystate);
}

tty_mode(int how){
  static struct termios original_mode;
  if(how == 0)
    tcgetattr(0, &original_mode);
  else
    return tcsetattr(0, TCSANOW, &original_mode);
}

int printFile (const char* fileName)
{
  FILE* file;
  file = fopen(fileName,"r");
  if (!file) {
      perror("unable to open file");
      return -1;
  }
  tty_mode(0);
  int i = 0;
  int usrinput;
  printfile(file);
  printf("\033[7m");
  printf("%s",fileName);
  printf("\033[m");
  setNodelay();
  while((usrinput = fgetc(stdin))!= 'q' && fgetc(file) != EOF)
  {
    if(usrinput == ' '){
      printf("\b \b");
      printfile(file);
    }
    else if(usrinput == '\n'){
      printline(file);
    }
  }
  tty_mode(1);
  if (fclose (file)) {
      perror("unable to close file");
      return -1;
  }
  return 0;
}

void intoOut(){
  int input;
  int fd_tty;
  FILE* fp_tty;
  fd_tty = open("/dev/tty",O_RDONLY);
  fp_tty = fdopen(fd_tty,"r");
  while((input = fgetc(fp_tty))!=EOF){//input,MAX,fp_tty)!=NULL){
    printf("%c",input);
    //printf("\033[7m");
   // printf("\033[m");
  }
}

int main (int argc,char* argv[])
{
    int status = 0;
    signal(SIGINT,signalResponse);
    if(argc == 1)
    {
      intoOut();
    }
    else
    {
      status = printFile(argv[1]);
    }
    return status;
}
