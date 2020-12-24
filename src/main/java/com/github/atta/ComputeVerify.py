from pycocoevalcap.bleu.bleu import Bleu
from pycocoevalcap.cider.cider import Cider
from pycocoevalcap.meteor.meteor import Meteor
gts={1:['i love her']}
res={1:['i love him']}
scorer=Meteor()
print(scorer.compute_score(gts,res))