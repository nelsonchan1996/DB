default:UI

%: %.java
	javac $@.java Database.java Table.java Record.java
	java -ea $@
