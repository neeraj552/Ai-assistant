package com.neeraj.assistant.ai.prompt;

public final class PromptTemplates {

    private PromptTemplates(){}

     public static final String SUMMARY_PROMPT = """
            You are an expert document summarizer.

            Summarize the following document in a clear and concise manner.

            Keep important facts, remove repetition, and return only the summary.

            Document:
            %s String prompt = PromptTemplates.SUMMARY_PROMPT.formatted(pdfText);
            """;

}
