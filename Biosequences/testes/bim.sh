for i in $(seq 20); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n3 >> testes/xf100n3.txt; done 
for i in $(seq 20); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n3 -w testes/weights100 >> testes/xf100n3w.txt; done
for i in $(seq 20); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -x testes/xae1.seq -y testes/xcc2.seq -m n3 >> testes/xae140n3.txt; done

for i in $(seq 10); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n3l >> testes/xf100n3l.txt; done 
for i in $(seq 10); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n3l -w testes/weights100 >> testes/xf100n3lw.txt; done
for i in $(seq 10); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -x testes/xae1.seq -y testes/xcc2.seq -m n3l >> testes/xae140n3l.txt; done

for i in $(seq 50); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m sn4 >> testes/xf100sn4.txt; done 
for i in $(seq 50); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m sn4 -w testes/weights100 >> testes/xf100sn4w.txt; done
for i in $(seq 10); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -x testes/xae1.seq -y testes/xcc2.seq -m sn4 >> testes/xae140sn4.txt; done

for i in $(seq 5); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n4 >> testes/xf100n4.txt; done 
for i in $(seq 5); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement100-r2-q1-G0-E10.compmat -m n4 -w testes/weights100 >> testes/xf100n4w.txt; done
for i in $(seq 5); do java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -x testes/xae1.seq -y testes/xcc2.seq -m n4 >> testes/xae140n4.txt; done

java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n4 >> testes/xf20n4.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n4 -w testes/weights100 >> testes/xf20n4w.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -x testes/xae420.seq -y testes/xcc418.seq -m n4 >> testes/xae420xcc418n4.txt

java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m sn4 >> testes/xf20sn4.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m sn4 -w testes/weights100 >> testes/xf20sn4w.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n3 >> testes/xf20n3.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n3 -w testes/weights100 >> testes/xf20n3w.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n3l >> testes/xf20n3l.txt
java -Xms64m -cp sequences.jar sequences/ui/Bim -i 2 -t 45 -b --time -f testes/XF02270239-PA47274746-window100-displacement20-r2-q1-G0-E10.compmat -m n3l -w testes/weights100 >> testes/xf20n3lw.txt

