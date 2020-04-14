# Erebus Alpha
# Copyright (C) 2015, 2020  Jie Kang

# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License along
# with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

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
	echo -e \#\!/bin/bash'\n'java -jar $(BUILD_DIR)/$(JAR) "$$"* > $(BIN_FILE) ; \
	chmod +x $(BIN_FILE) ; \
