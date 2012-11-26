all: parta

run:parta
	./parta
	./partb
	./partc
	./partd

parta:parta.c
	gcc -g parta.c -o parta -lpthread
	gcc -g partb.c -o partb -lpthread
	gcc -g partc.c -o partc -lpthread
	gcc -g partd.c -o partd -lpthread

clean:
	rm parta
	rm partb
	rm partc
	rm partd
