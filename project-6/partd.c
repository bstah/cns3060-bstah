#include <stdio.h>
#include <pthread.h>
int count;
pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;
void *incrementCounter(void* m){
  pthread_mutex_lock(&mutex1);
  int i;
  for(i=0;i<10;++i)
  {
    int tempValue = count;
    sleep(1);
    tempValue = tempValue + 1;
    count = tempValue;
  }
  pthread_mutex_unlock(&mutex1);
}

int main(){
  int s;
  count = 0;
  pthread_t thread1;
  pthread_t thread2;
  pthread_t thread3;
  pthread_t thread4;
  
  pthread_create(&thread1,NULL,&incrementCounter,NULL);
  pthread_create(&thread2,NULL,&incrementCounter,NULL);
  pthread_create(&thread3,NULL,&incrementCounter,NULL);
  pthread_create(&thread4,NULL,&incrementCounter,NULL);

  pthread_join(thread1,NULL);
  pthread_join(thread2,NULL);
  pthread_join(thread3,NULL);
  pthread_join(thread4,NULL);
  printf("%i\n",count);
  return 0;
}
