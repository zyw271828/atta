from pycocoevalcap.bleu.bleu import Bleu
from pycocoevalcap.cider.cider import Cider
from pycocoevalcap.meteor.meteor import Meteor

if __name__ == '__main__':
    with open("in.txt", "r") as f:
        data = f.readlines()
        gts = {1: [data[0]]}
        res = {1: [data[1]]}
        # print(gts)
        # print(res)
    scorer = Bleu()
    scorer._n = 4
    ret, rets = scorer.compute_score(gts, res)
    print(100 * (1 - ret[3]))
