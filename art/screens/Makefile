# make jpegs from pngs
PNGS := $(wildcard *.png)
JPGS := $(patsubst %.png,%.jpg,$(PNGS))

all: $(JPGS)

%.jpg: %.png
	./convertit $< $@

clean:
	rm -f $(JPGS)