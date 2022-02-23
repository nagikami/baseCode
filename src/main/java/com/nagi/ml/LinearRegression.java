package com.nagi.ml;

public class LinearRegression {
    public static void main(String[] args) {
        //y = 100 + 10x;
        int[] xData = new int[] {1, 3, 5, 6, 8, 10};
        int[] yData = new int[] {110, 130, 150, 160, 180, 200};
        sameLearningRate(xData, yData);
        disLearningRate(xData, yData);
    }

    /**
     * b,w 相同的学习速率
     * @param xData
     * @param yData
     */
    public static void sameLearningRate(int[] xData, int[] yData) {
        int iteration = 10000;
        double lr = 0.001;
        double b = 2;
        double w = 1;
        for (int i = 0; i < iteration; i++) {
            double bGrad = 0;
            double wGrad = 0;
            for (int j = 0; j < xData.length; j++) {
                bGrad -= 2 * (yData[j] - b - w * xData[j]);
                wGrad -= 2 * (yData[j] - b - w * xData[j]) * xData[j];
            }
            b -= lr * bGrad;
            w -= lr * wGrad;
        }
        System.out.println("b: " + b + " w: " + w);
    }

    /**
     * adaptive learning rate
     * b,w 不同的学习速率(AdaGrad)
     */
    public static void disLearningRate(int[] xData, int[] yData) {
        int iteration = 100000;
        double lr = 1;
        double b = 2;
        double w = 1;
        //root mean square (sqrt(1/2(a * a + b * b)))
        double lr_b = 0;
        double lr_w = 0;
        for (int i = 0; i < iteration; i++) {
            double bGrad = 0;
            double wGrad = 0;
            for (int j = 0; j < xData.length; j++) {
                bGrad -= 2 * (yData[j] - b - w * xData[j]);
                wGrad -= 2 * (yData[j] - b - w * xData[j]) * xData[j];
            }
            lr_b = lr_b + Math.pow(bGrad, 2);
            lr_w = lr_w + Math.pow(wGrad, 2);
            b -= lr / (Math.sqrt(lr_b)) * bGrad;
            w -= lr / (Math.sqrt(lr_w)) * wGrad;
        }
        System.out.println("b: " + b + " w: " + w);
    }
}
