# gff3-to-model
GFF3 reader code that generates model objects from lines of GFF3 as described at the reference link below.

https://github.com/The-Sequence-Ontology/Specifications/blob/master/gff3.md 

At time of initial development, the reference link was at http://www.eu-sol.net/science/bioinformatics/standards-documents/gff3-format-description.
However, that was in early 2011, and the eo-sol link is now either missing or forbidden.

The descriptions of the fields encountered at the initial development were embedded as comment text in the GFF3LineReader class.

# What this Code Does
If you use this code, you should be able to feed a file into its reader, and get models representing those lines, with referencing links
between them.  Think of it a bit as using a DOM model.  Running the two lines below, given the A Fumigatus test data bundled with this package,
will produce a model suitable for querying with getters, etc.

    Gff3DataAssembler assembler = new Gff3DataAssembler( "/path/to/file.gff3" );
    assembler.prepareModels( "A_fumigatus_Af293_Chr1" );

## Unit Tests
The unit tests included in the test area of this Gradle project may serve to further illustrate what can be done.

# Complexities
The GFF3 format is quite simple in nature, in that all of its lines can be read in a program written in a short time by even fairly
inexperienced programmers, and which probably has a line-wise library readily available in all commonly-used programming languages.
Anything that could read a "BED" file or "tab-separated variable field-length" file could read GFF3 at its basic level.  That said,
some complexities arise when interpreting contents of a field or interrelationships between items in a file.   GFF3 specifies parent
child relationships between various types.

As long as a specific format (such as the one in the reference link) is followed, and assuming the relationships are as expected
by this reader, the models produces should correspond to the intended semantics of the file.  However, please be aware that complexities
such as this may exist for a simple format and bear that in mind as you are using this code.

This code draws data into memory.  For some applications, especially those using only one or two fields of data, this may be wasteful.

# Future Plans
In future, the author may make additonal changes to this code, such as modernizing it for a fluent / functional code paradigm.  This
may only be the case if time permits, however.

# Conclusions
Thank you for your interest.
