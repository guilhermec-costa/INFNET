.PHONY: all clean run

OUTPUT_DIR = output
JAVA_FILES = App.java Client.java Item.java Order.java EmailService.java InvoiceFormatter.java DiscountPolicy.java
CLASSES = $(JAVA_FILES:%.java=$(OUTPUT_DIR)/%.class)

all: $(OUTPUT_DIR) $(CLASSES)

$(OUTPUT_DIR):
	mkdir -p $(OUTPUT_DIR)

$(OUTPUT_DIR)/%.class: %.java | $(OUTPUT_DIR)
	javac -d $(OUTPUT_DIR) $<

run: all
	java -cp $(OUTPUT_DIR) App

clean:
	rm -rf $(OUTPUT_DIR)
