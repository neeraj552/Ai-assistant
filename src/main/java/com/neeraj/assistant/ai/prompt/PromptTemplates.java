package com.neeraj.assistant.ai.prompt;

public final class PromptTemplates {

    private PromptTemplates() {}

    public static final String SUMMARY_PROMPT = """
            You are an expert document summarizer.

            Summarize the following document in a clear and concise manner.

            Keep important facts, remove repetition, and return only the summary.

            Document:
            %s
            """;

    public static final String CHAT_PROMPT = """
        You are an AI assistant that answers questions using ONLY the provided context.

        Rules:
        1. Answer ONLY using the provided context.
        2. If the answer is not present in the context, reply:
           "I couldn't find that information in the uploaded document."
        3. Do not make up facts or use outside knowledge.
        4. Keep the answer clear, concise, and accurate.

        Context:
        -----------------------
        %s
        -----------------------

        Question:
        %s

        Answer:
        """;
}