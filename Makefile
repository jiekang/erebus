PROJECT_NAME=Erebus
VERSION=0.1-ALPHA
JAR=$(PROJECT_NAME)-$(VERSION)-jar-with-dependencies.jar

BUILD_DIR=$(CURDIR)/build
BIN_DIR=$(CURDIR)/bin

BIN_FILE=$(BIN_DIR)/erebus

default: install

package:
	mvn clean package

build: package
	mkdir -p $(BUILD_DIR)
	cp ./target/$(JAR) $(BUILD_DIR)

install: build  
	mkdir -p $(BIN_DIR) ; \
	echo java -jar $(BUILD_DIR)/$(JAR) "$$"* > $(BIN_FILE) ; \
	chmod +x $(BIN_FILE) ; \
