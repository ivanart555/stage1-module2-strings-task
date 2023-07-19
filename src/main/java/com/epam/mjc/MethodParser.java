package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String accessModifier = null;
        String returnType;
        String methodName;
        List<MethodSignature.Argument> arguments = new ArrayList<>();
        boolean hasAccessModifier;

        List<String> parsedModifierTypeName;
        List<String> parsedArguments = new ArrayList<>();

        StringTokenizer stringTokenizer = new StringTokenizer(signatureString, "\\Q()");

        if (stringTokenizer.hasMoreTokens()) {
            parsedModifierTypeName = (Arrays.asList(stringTokenizer.nextToken().split(" ")));
            hasAccessModifier = parsedModifierTypeName.size() != 2;
        } else throw new MethodParserException("Failed to parse method signature (modifier, type, name).");

        if (stringTokenizer.hasMoreTokens()) {
            StringTokenizer st = new StringTokenizer(stringTokenizer.nextToken(), " ,");
            while (st.hasMoreTokens())
                parsedArguments.add(st.nextToken());
        }

        try {
            if (hasAccessModifier) {
                accessModifier = parsedModifierTypeName.get(0);
                returnType = parsedModifierTypeName.get(1);
                methodName = parsedModifierTypeName.get(2);
            } else {
                returnType = parsedModifierTypeName.get(0);
                methodName = parsedModifierTypeName.get(1);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new MethodParserException("Failed to parse method signature. Maybe missed delimiters.", e);
        }

        for (int i = 0; i < parsedArguments.size(); i += 2) {
            arguments.add(new MethodSignature.Argument(parsedArguments.get(i), parsedArguments.get(i + 1)));
        }

        MethodSignature methodSignature = new MethodSignature(null, arguments);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);
        methodSignature.setMethodName(methodName);

        return methodSignature;
    }
}
