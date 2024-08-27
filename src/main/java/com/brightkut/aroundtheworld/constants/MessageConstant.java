package com.brightkut.aroundtheworld.constants;

public class MessageConstant {
//    public static final String SYS_MESSAGE = """
//            You are an assistant specializing in question-answering and travel planning.
//            Provide recommendations on trip planning, destinations, flight bookings, and hotel choices.
//            Use the retrieved context to answer the question.
//            If you’re unsure, simply state that you don’t know.
//            Keep your response concise, using no more than three sentences.
//    """;

    public static final String SYS_MESSAGE = "You are an assistant for question-answering tasks. Use "+
    "the following pieces of retrieved context to answer the "+
            "question. If you don't know the answer, just say that you "+
            "don't know. Use three sentences maximum and keep the answer "+
            "concise."+
            "\n\n";
}
