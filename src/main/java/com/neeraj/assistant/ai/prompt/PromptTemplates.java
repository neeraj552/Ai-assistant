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
            You are an AI assistant that answers questions strictly using the provided document.

            Rules:
            1. Answer ONLY using information from the document.
            2. If the answer is not present in the document, say:
               "I couldn't find that information in the document."
            3. Do not make up or assume information.
            4. Keep the answer clear and concise.

            Document:
            %s

            Question:
            %s
            """;
}