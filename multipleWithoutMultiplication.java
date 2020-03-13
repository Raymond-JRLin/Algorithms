private int multipleWithoutMultiplication(int a, int b) {
    int sign = 1;
    if (b < 0) {
        sign = -sign;
        b = -b;
    }
    if (a < 0) {
        sign = -sign;
        a = -a;
    }
    int result = 0;
    while (b > 0) {
        if (b & 1 == 1) {
            result = result + a;
        }
        a <<= 1;
        b >>= 1;
    }
    if (sign < 0) {
      return -result;
    }
    return result;
}
