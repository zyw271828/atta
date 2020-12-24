#!/usr/bin/env python3

import sys

from pycocoevalcap.bleu.bleu import Bleu


def main():
    if len(sys.argv) < 3:
        print('Usage: ' + sys.argv[0] + ' str1 str2')
        exit()
    else:
        gts = {1: [sys.argv[1]]}
        res = {1: [sys.argv[2]]}
        scorer = Bleu()
        scorer._n = 4
        ret, _ = scorer.compute_score(gts, res, verbose=0)
        print(100 * ret[3])


if __name__ == '__main__':
    main()
