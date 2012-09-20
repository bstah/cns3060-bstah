#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(int argc, char* argv[])
{

if(argc == 1)
{
	char Input[250];
	while(fgets(Input,250,stdin)!=NULL)
	{
		printf("%s", Input);
	}
}
else if(argc > 2 && strcmp(argv[1],"-n")==0)
{
	FILE *open;
	int num = 1;
	int i = 2;
	while(i<argc)
	{
	  open = fopen(argv[i],"r");
	  if(open == NULL)
	  {
		printf("unable to open file");
		return -1;
	  }
	  char file[250];
	  while(fgets(file,250,open)!=NULL)
	  {
		printf("%i%s",num,file);
		num++;
	  }
	  fclose(open);
	  i++;
	}
}
else if(argc == 2 && strcmp(argv[1], "-n") == 0)
{
	char in[250];
	int num = 1;
	while(fgets(in,250,stdin)!=NULL)
	{
		printf("\t%i%s",num,in);
		num++;
	}
}
else if(argc > 1)
{
	FILE *open;
	char file[250];
	int i = 1;
	while(i<argc)
	{
	  open = fopen(argv[i],"r");
	  if(open == NULL)
	  {
		printf("unable to open file");
		return -1;
	  }
	  while(fgets(file,250,open)!=NULL)
	  {
		printf("%s",file);
	  }
	  fclose(open);
	  i++;
	}
}
return 0;
}
